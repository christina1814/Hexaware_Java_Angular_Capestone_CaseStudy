import { Component, OnInit } from '@angular/core';
import { EmployerService } from 'src/app/services/employer.service';
import { AuthService } from 'src/app/services/auth.service';
import { EmployerProfile } from 'src/app/models/employer-profile.model';

@Component({
  selector: 'app-employer-home',
  templateUrl: './employer-home.component.html',
  styleUrls: ['./employer-home.component.css']
})
export class EmployerHomeComponent implements OnInit {
  profile: EmployerProfile | null = null;
  isEdit = false;
  errorMessage = '';
  loadingProfile = true;

  constructor(
    private employerService: EmployerService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadProfile();
  }

  loadProfile(): void {
    const userId = this.authService.getUserId();

    if (!userId) {
      this.loadingProfile = false;
      this.errorMessage = 'User not authenticated.';
      return;
    }

    this.employerService.getProfileByUserId(userId).subscribe({
      next: (res) => {
        if (!res) {
          this.errorMessage = 'No profile found.';
          return;
        }

        this.profile = res;
        localStorage.setItem('employerId', res.id!.toString());
      },
      error: (err) => {
        console.error('Error fetching profile:', err);
        this.errorMessage = err?.error || 'Failed to load profile.';
      },
      complete: () => {
        this.loadingProfile = false;
      }
    });
  }

  toggleEdit(): void {
    this.isEdit = !this.isEdit;
  }

  updateProfile(): void {
    if (this.profile && this.profile.userId) {
      this.employerService.updateProfileByUserId(this.profile.userId, this.profile).subscribe({
        next: () => {
          alert('Profile updated successfully!');
          this.isEdit = false;
        },
        error: (err) => {
          console.error('Update failed:', err);
          this.errorMessage = err?.error || 'Failed to update profile. Please try again.';
        }
      });
    }
  }
}
