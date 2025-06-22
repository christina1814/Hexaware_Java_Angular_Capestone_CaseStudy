import { Component, OnInit } from '@angular/core';
import { ApplicationService } from 'src/app/services/application.service';
import { JobseekerApplication } from 'src/app/models/application-response.model';

@Component({
  selector: 'app-jobseeker-applications',
  templateUrl: './jobseeker-applications.component.html',
  styleUrls: ['./jobseeker-applications.component.css']
})
export class JobseekerApplicationsComponent implements OnInit {
  applications: JobseekerApplication[] = [];
  errorMessage = '';

  constructor(private applicationService: ApplicationService) {}

  ngOnInit(): void {
    this.loadApplications();
  }

  loadApplications(): void {
    const seekerIdStr = localStorage.getItem('jobseekerId');

    if (!seekerIdStr) {
      this.errorMessage = 'Jobseeker ID not found. Please complete your profile.';
      return;
    }

    const seekerId = +seekerIdStr;

    this.applicationService.getApplicationsForJobseeker(seekerId).subscribe({
      next: (res) => {
        this.applications = res;
      },
      error: (err) => {
        console.error('Error fetching applications:', err);
      }
    });
  }

  withdraw(applicationId: number): void {
    if (confirm('Are you sure you want to withdraw this application?')) {
      this.applicationService.withdrawApplication(applicationId).subscribe({
        next: () => {
          this.applications = this.applications.filter(app => app.applicationId !== applicationId);
          this.showToast('Application withdrawn successfully');
        },
        error: (err) => {
          console.error('Withdraw failed:', err);
          this.showToast('Failed to withdraw application', 'error');
        }
      });
    }
  }

  private showToast(message: string, type: string = 'success'): void {
    alert(`${type === 'success' ? '✓' : '✗'} ${message}`);
  }
}
