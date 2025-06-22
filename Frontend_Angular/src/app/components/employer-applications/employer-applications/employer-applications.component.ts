import { Component, OnInit } from '@angular/core';
import { JobService } from 'src/app/services/job.service';
import { ApplicationService } from 'src/app/services/application.service';
import { JobPost } from 'src/app/models/job-post.model';
import { ApplicationSummary } from 'src/app/models/application-response.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-employer-applications',
  templateUrl: './employer-applications.component.html',
  styleUrls: ['./employer-applications.component.css']
})
export class EmployerApplicationsComponent implements OnInit {
  jobs: JobPost[] = [];
  applicationsMap: { [jobId: number]: ApplicationSummary[] } = {};
  showApps: { [jobId: number]: boolean } = {};
  loadingApps: { [jobId: number]: boolean } = {};
  errorMessage = '';
  loadingJobs = true;

  constructor(
    private jobService: JobService,
    private applicationService: ApplicationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const employerIdStr = localStorage.getItem('employerId');
    if (employerIdStr) {
      const employerId = +employerIdStr;
      this.jobService.getJobsByEmployer(employerId).subscribe({
        next: (jobs) => {
          this.jobs = jobs;
          this.loadingJobs = false;
        },
        error: (err) => {
          console.error('Error fetching jobs:', err);
          this.errorMessage = 'Failed to load job postings. Please try again.';
          this.loadingJobs = false;
        }
      });
    } else {
      this.errorMessage = 'Employer ID not found. Please login or complete your profile.';
      this.loadingJobs = false;
    }
  }

  goToPostJob() {
  this.router.navigate(['/post-job-form']);
}


  toggleApplications(jobId: number): void {
    if (!this.showApps[jobId]) {
      this.loadingApps[jobId] = true;

      this.applicationService.getApplicationsByJob(jobId).subscribe({
        next: (apps) => {
          this.applicationsMap[jobId] = apps || [];
          this.showApps[jobId] = true;
          this.loadingApps[jobId] = false;
        },
        error: () => {
          this.applicationsMap[jobId] = [];
          this.showApps[jobId] = true;
          this.loadingApps[jobId] = false;
        }
      });
    } else {
      this.showApps[jobId] = !this.showApps[jobId];
    }
  }

  updateStatus(appId: number, newStatus: string, jobId: number): void {
    this.applicationService.updateApplicationStatus(appId, newStatus).subscribe({
      next: (res) => {
        const app = this.applicationsMap[jobId].find(a => a.applicationId === appId);
        if (app) app.status = newStatus;
        alert(`Status updated: ${res}`);
      },
      error: () => {
        alert('Failed to update status');
      }
    });
  }
}