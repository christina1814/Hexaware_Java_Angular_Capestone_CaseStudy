import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html'
})
export class NavbarComponent {

  searchQuery: string = '';

  constructor(private router: Router) { }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  isEmployer(): boolean {
    return localStorage.getItem('role') === 'EMPLOYER';
  }

  isJobSeeker(): boolean {
    return localStorage.getItem('role') === 'JOB_SEEKER';
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/home']);
  }

  isLoginOrRegisterPage(): boolean {
    return this.router.url === '/login' || this.router.url === '/register';
  }

  showLogoutButton(): boolean {
    return this.isLoggedIn() && !this.isLoginOrRegisterPage();
  }

}
