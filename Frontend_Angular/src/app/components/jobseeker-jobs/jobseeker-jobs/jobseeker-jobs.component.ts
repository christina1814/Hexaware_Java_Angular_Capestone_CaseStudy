import { Component, OnInit } from '@angular/core';
import { JobService } from 'src/app/services/job.service';
import { JobPost } from 'src/app/models/job-post.model';
import { JobSeekerService } from 'src/app/services/jobseeker.service';
import { JobSeekerProfile } from 'src/app/models/jobseeker-profile.model';
import { ApplicationService } from 'src/app/services/application.service';

@Component({
  selector: 'app-jobseeker-jobs',
  templateUrl: './jobseeker-jobs.component.html',
  styleUrls: ['./jobseeker-jobs.component.css']
})
export class JobseekerJobsComponent implements OnInit {
  jobs: JobPost[] = [];
  profile!: JobSeekerProfile;
  resumePath: string | null = null;
  errorMessage = '';
    colorPalette = ['#667eea', '#764ba2', '#6B8DD6', '#8E37D7', '#5B7DBF'];


  constructor(
    private jobService: JobService,
    private jobSeekerService: JobSeekerService,
    private applicationService: ApplicationService
  ) { }

  ngOnInit(): void {
    this.jobService.getAllJobs().subscribe({
      next: (res) => this.jobs = res,
      error: () => this.errorMessage = 'Failed to fetch job listings'
    });

    const userId = localStorage.getItem('userId');
    this.resumePath = localStorage.getItem('resume');

    if (userId) {
      this.jobSeekerService.getProfileByUserId(+userId).subscribe({
        next: (res) => {
          this.profile = res;
          localStorage.setItem('jobseekerId', res.id.toString());
        },
        error: () => this.errorMessage = 'Unable to fetch profile details'
      });
    }
  }

  apply(jobId: number): void {
    const seekerId = this.profile?.id;
    const resumePath = localStorage.getItem('resume');

    if (!resumePath || resumePath.trim() === '') {
      alert('Please update your profile first.');
      return;
    }

    const payload = { jobId, seekerId, resumePath };

    this.applicationService.applyToJob(payload).subscribe({
      next: () => alert('Applied successfully!'),
      error: (err) => {
        const message = err.error?.message || err.error || 'Application failed.';
        alert(message);
      }
    });
  }

  getRandomColor(): string {
    return this.colorPalette[Math.floor(Math.random() * this.colorPalette.length)];
  }
}