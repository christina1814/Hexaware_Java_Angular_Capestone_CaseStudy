import { Component, OnInit } from '@angular/core';
import { JobSeekerService } from 'src/app/services/jobseeker.service';
import { AuthService } from 'src/app/services/auth.service';
import { JobSeekerProfile } from 'src/app/models/jobseeker-profile.model';

@Component({
  selector: 'app-jobseeker-home',
  templateUrl: './jobseeker-home.component.html',
  styleUrls: ['./jobseeker-home.component.css']
})
export class JobseekerHomeComponent implements OnInit {
  profile: JobSeekerProfile | null = null;
  isEdit = false;
  skillsInput = '';
  errorMessage = '';
  loadingProfile = true;

  constructor(
    private jobSeekerService: JobSeekerService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadProfile();
  }

  loadProfile(): void {
    const userId = this.authService.getUserId();

    if (!userId) {
      this.loadingProfile = false;
      return;
    }

    this.jobSeekerService.getProfileByUserId(userId).subscribe({
      next: (res) => {
        if (!res) {
          this.errorMessage = 'No profile found.';
          return;
        }

        this.profile = {
          id: res.id,
          fullName: res.fullName,
          education: res.education,
          experience: res.experience,
          resume: res.resume,
          phoneNumber: res.phoneNumber,
          userId: res.userId,
          skills: res.skills
        };

        this.skillsInput = this.profile.skills.join(', ');
        localStorage.setItem('jobseekerId', this.profile.id.toString());

        if (this.profile.resume) {
          localStorage.setItem('resume', this.profile.resume);
        }
      },
      error: (err) => {
        console.error('Error fetching profile:', err);
        this.errorMessage = err?.error || 'Failed to load profile.';
      },
      complete: () => this.loadingProfile = false
    });
  }

  toggleEdit(): void {
    this.isEdit = !this.isEdit;
  }

  updateProfile(): void {
    const jobseekerId = localStorage.getItem('jobseekerId');

    if (!jobseekerId || !this.profile) return;

    this.profile.skills = this.skillsInput
      .split(',')
      .map(s => s.trim())
      .filter(s => s.length > 0);

    const id = parseInt(jobseekerId, 10);

    this.jobSeekerService.updateProfileByJobSeekerId(id, this.profile).subscribe({
      next: (res) => {
        alert('Profile updated successfully!');
        this.isEdit = false;

        if (this.profile?.resume) {
          localStorage.setItem('resume', this.profile.resume);
        }
      },
      error: (err) => {
        console.error('Error updating profile:', err);
        this.errorMessage = err?.error || 'Update failed. Try again.';
      }
    });
  }
}