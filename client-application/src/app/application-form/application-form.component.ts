import { Component, OnInit } from '@angular/core';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { ApplicationService } from '../services/application.service';
import { FormsModule } from '@angular/forms';
import { Application } from '../models/application.model';
import { CommonModule } from '@angular/common'; 

@Component({
  selector: 'app-application-form',
  imports: [RouterModule, FormsModule, CommonModule],
  templateUrl: './application-form.component.html',
  styleUrls: ['./application-form.component.scss'],
  standalone: true
})
export class ApplicationFormComponent implements OnInit {
  raceId: string = '';
  application: Application = { firstName: '', lastName: '', club: '', raceId: '' };
  submissionErrors: any = {};

  constructor(
    private applicationService: ApplicationService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.raceId = params.get('id')!;
      this.application.raceId = this.raceId;
    });
  }

  apply(): void {
    this.applicationService.applyToRace(this.application).subscribe({
      next: (data) => {
        this.router.navigate(['/race-list']);
      },
      error: (err) => {
        if (err.status === 400 && err.error) {
          this.submissionErrors = err.error;
        } else {
          console.error('Error submitting application:', err);
        }
      }
    });
  }
}
