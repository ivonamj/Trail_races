import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ApplicationService } from '../services/application.service';
import { RaceService } from '../services/race.service';
import { CommonModule } from '@angular/common';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-application-list',
  imports: [RouterModule, CommonModule],
  templateUrl: './application-list.component.html',
  styleUrls: ['./application-list.component.scss'],
  standalone: true
})
export class ApplicationListComponent implements OnInit {
  applications: any[] = [];
  raceNames: { [key: string]: string } = {};

  constructor(
    private applicationService: ApplicationService,
    private raceService: RaceService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadApplications();
  }

  loadApplications(): void {
    const userId = this.authService.getUserIdFromToken();

    if (!userId) {
      console.error('User ID is missing from token');
      return;
    }

    this.applicationService.getApplicationsForUser(userId).subscribe((data) => {
      this.applications = data;

      const uniqueRaceIds = [...new Set(this.applications.map((app) => app.raceId))];
      uniqueRaceIds.forEach((raceId) => {
        this.raceService.getRaceById(raceId).subscribe({
          next: (race) => {
            this.raceNames[raceId] = race.name;
          },
          error: () => {
            console.error('Error loading race.');
          },
        });
      });
    });
  }

  deleteApplication(id: string): void {
    this.applicationService.deleteApplication(id).subscribe(() => {
      this.loadApplications();
    });
  }

  getRaceName(raceId: string): string {
    return this.raceNames[raceId] || 'Loading...';
  }
}
