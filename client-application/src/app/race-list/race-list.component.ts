import { Component, OnInit } from '@angular/core';
import { RouterModule, Router } from '@angular/router';
import { RaceService } from '../services/race.service';
import { AuthService } from '../services/auth.service';
import { CommonModule } from '@angular/common';
import { ApplicationService } from '../services/application.service';

@Component({
  selector: 'app-race-list',
  imports: [RouterModule, CommonModule],
  templateUrl: './race-list.component.html',
  styleUrls: ['./race-list.component.scss'],
  standalone: true
})
export class RaceListComponent implements OnInit {
  races: any[] = [];
  appliedRaceIds: string[] = [];
  isAdministrator: boolean = false;
  isApplicant: boolean = false;

  constructor(
    private raceService: RaceService,
    private applicationService: ApplicationService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.isAdministrator = this.authService.isAdministrator();
    this.isApplicant = this.authService.isApplicant();
    this.loadRaces();
    if (this.isApplicant) {
      this.loadApplications();
    }
  }

  loadRaces(): void {
    this.raceService.getAllRaces().subscribe((data) => {
      this.races = data;
    });
  }

  loadApplications(): void {
    const userId = this.authService.getUserIdFromToken();
    if (userId) {
      this.applicationService.getApplicationsForUser(userId).subscribe((applications) => {
        this.appliedRaceIds = applications.map((app) => app.raceId);
      });
    }
  }

  applyToRace(raceId: string): void {
    const userId = this.authService.getUserIdFromToken();
    if (userId) {
      const application = { userId, raceId, firstName: 'John', lastName: 'Doe' };
      this.applicationService.applyToRace(application).subscribe(() => {
        this.loadApplications();
      });
    }
  }

  deleteRace(id: string): void {
    if (this.isAdministrator) {
      this.raceService.deleteRace(id).subscribe(() => {
        this.loadRaces();
      });
    }
  }

  navigateToEditRace(id: string): void {
    this.router.navigate(['/race-form', id]);
  }
  
  navigateToCreateRace(): void {
    this.router.navigate(['/race-form']);
  }  
}
