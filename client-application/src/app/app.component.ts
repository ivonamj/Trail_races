import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, Router, NavigationEnd } from '@angular/router';
import { HeaderComponent } from './header/header.component';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderComponent, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'client-application';
  showHeader = false;
  private tokenExpiryTimeout!: any;

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.showHeader = !event.urlAfterRedirects.startsWith('/login');
      }
    });

    const token = localStorage.getItem('token');

    if (token) {
      if (this.authService.isTokenExpired(token)) {
        this.handleTokenExpiry();
      } else {
        this.scheduleTokenExpiryCheck(token);
      }
    }
  }

  ngOnDestroy(): void {
    clearTimeout(this.tokenExpiryTimeout);
  }

  private scheduleTokenExpiryCheck(token: string): void {
    const expiryTime = this.authService.getTokenExpiryTime(token);
    const timeUntilExpiry = expiryTime - Date.now();

    if (timeUntilExpiry > 0) {
      this.tokenExpiryTimeout = setTimeout(() => {
        this.handleTokenExpiry();
      }, timeUntilExpiry);
    } else {
      this.handleTokenExpiry();
    }
  }

  private handleTokenExpiry(): void {
    console.warn('Token has expired. Redirecting to login...');
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }
}
