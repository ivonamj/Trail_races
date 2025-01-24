import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Race } from '../models/race.model';

@Injectable({
  providedIn: 'root'
})
export class RaceService {
  private commandApiUrl = 'http://localhost:8081/races';
  private queryApiUrl = 'http://localhost:8082/races';

  constructor(
    private http: HttpClient
  ) {}

  getAllRaces(): Observable<Race[]> {
    return this.http.get<Race[]>(this.queryApiUrl);
  }

  getRaceById(id: string): Observable<Race> {
    return this.http.get<Race>(`${this.queryApiUrl}/${id}`);
  }

  createRace(race: Race): Observable<Race> {
    return this.http.post<Race>(this.commandApiUrl, race);
  }

  updateRace(id: string, race: Race): Observable<Race> {
    return this.http.patch<Race>(`${this.commandApiUrl}/${id}`, race);
  }

  deleteRace(id: string): Observable<void> {
    return this.http.delete<void>(`${this.commandApiUrl}/${id}`);
  }
}
