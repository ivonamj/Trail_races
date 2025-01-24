import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Application } from '../models/application.model';

@Injectable({
  providedIn: 'root'
})
export class ApplicationService {
  private commandApiUrl = 'http://localhost:8081/applications';
  private queryApiUrl = 'http://localhost:8082/applications';

  constructor(
    private http: HttpClient
  ) {}

  getAllApplications(): Observable<Application[]> {
    return this.http.get<Application[]>(this.queryApiUrl);
  }

  getApplicationsForUser(userId: string): Observable<Application[]> {
    return this.http.get<Application[]>(`${this.queryApiUrl}?userId=${userId}`);
  }

  applyToRace(application: Application): Observable<Application> {
    return this.http.post<Application>(this.commandApiUrl, application);
  }

  deleteApplication(id: string): Observable<void> {
    return this.http.delete<void>(`${this.commandApiUrl}/${id}`);
  }
}
