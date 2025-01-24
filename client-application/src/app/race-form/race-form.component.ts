import { Component, OnInit } from '@angular/core';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { RaceService } from '../services/race.service';
import { FormsModule } from '@angular/forms';
import { Race } from '../models/race.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-race-form',
  imports: [RouterModule, FormsModule, CommonModule],
  templateUrl: './race-form.component.html',
  styleUrls: ['./race-form.component.scss'],
  standalone: true
})
export class RaceFormComponent implements OnInit {
  race: Race = { name: '', distance: '' };
  isUpdateMode: boolean = false;
  submissionErrors: any = {};

  constructor(
    private raceService: RaceService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const id = params.get('id');
      if (id) {
        this.isUpdateMode = true;
        this.raceService.getRaceById(id).subscribe((race: Race) => {
          this.race = race;
        });
      } else {
        this.isUpdateMode = false;
      }
    });
  }

  saveRace(): void {
    if (this.race.id) {
      this.raceService.updateRace(this.race.id, this.race).subscribe({
        next: (data) => {
          this.router.navigate(['/race-list']);
        },
        error: (err) => {
          this.handleSubmissionError(err);
        }
      });
    } else {
      this.raceService.createRace(this.race).subscribe({
        next: (data) => {
          this.router.navigate(['/race-list']);
        },
        error: (err) => {
          this.handleSubmissionError(err);
        }
      });
    }
  }

  private handleSubmissionError(error: any): void {
    if (error.status === 400 && error.error) {
      this.submissionErrors = error.error;
    } else {
      console.error('Error submitting application:', error);
    }
  }
}
