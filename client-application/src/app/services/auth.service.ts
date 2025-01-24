import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8081/auth';

  constructor(
    private http: HttpClient,
    private jwtHelper: JwtHelperService
  ) {}

  login(email: string, password: string): Observable<any> {
    return this.http.post<{ token: string }>(`${this.apiUrl}/login`, { email, password }).pipe(
      tap((response) => {
        if (response.token) {
          localStorage.setItem('token', response.token);
        } else {
          throw new Error('Invalid response: Missing token');
        }
      })
    );
  }  
  
  logout(): void {
    localStorage.removeItem('token');
  }

  getToken(): string {
    return localStorage.getItem('token') || '';
  }

  getUserIdFromToken(): string | null {
    const token = this.getToken();
    if (!token) {
      return null;
    }

    const decodedToken = this.jwtHelper.decodeToken(token);
    return decodedToken?.userId || null;
  }

  isTokenExpired(token: string): boolean {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const expiry = payload.exp * 1000;
      return Date.now() > expiry;
    } catch (error) {
      console.error('Error decoding token:', error);
      return true;
    }
  }

  getTokenExpiryTime(token: string): number {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.exp * 1000;
    } catch (error) {
      console.error('Error decoding token:', error);
      return 0;
    }
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    return token != null && !this.jwtHelper.isTokenExpired(token);
  }

  isAdministrator(): boolean {
    const token = this.getToken();
    const decodedToken = this.jwtHelper.decodeToken(token);
    return decodedToken?.role === 'ADMINISTRATOR';
  }

  isApplicant(): boolean {
    const token = this.getToken();
    const decodedToken = this.jwtHelper.decodeToken(token);
    return decodedToken?.role === 'APPLICANT';
  }  
}
