import { Component, OnInit } from '@angular/core';
import { JobService } from 'src/app/services/job.service';
import { JobPost } from 'src/app/models/job-post.model';

@Component({
  selector: 'app-my-posted-jobs',
  templateUrl: './my-posted-jobs.component.html',
  styleUrls: ['./my-posted-jobs.component.css']
})
export class MyPostedJobsComponent implements OnInit {
  jobList: JobPost[] = [];
  selectedJob: JobPost | null = null;
  editMode: boolean = false;
  loadingJobs: boolean = true;

  constructor(private jobService: JobService) {}

  ngOnInit(): void {
    this.loadJobs();
  }

  loadJobs(): void {
    this.loadingJobs = true;
    const employerIdStr = localStorage.getItem('employerId');

    if (employerIdStr) {
      const employerId = +employerIdStr;
      this.jobService.getJobsByEmployer(employerId).subscribe({
        next: (data) => {
          this.jobList = data;
          this.loadingJobs = false;
        },
        error: (err) => {
          console.error('Error fetching jobs:', err);
          this.loadingJobs = false;
        }
      });
    } else {
      alert('Employer ID not found in local storage. Please complete your profile.');
      this.loadingJobs = false;
    }
  }

  deleteJob(id: number): void {
    if (confirm('Are you sure you want to delete this job? This action cannot be undone.')) {
      this.jobService.deleteJobPosting(id).subscribe({
        next: () => {
          this.jobList = this.jobList.filter(job => job.id !== id);
          alert('Job deleted successfully');
        },
        error: err => {
          console.error('Error deleting job:', err);
          alert('Failed to delete job');
        }
      });
    }
  }

  editJob(job: JobPost): void {
    this.selectedJob = { ...job };
    this.editMode = true;
    document.body.style.overflow = 'hidden';
  }

  submitEdit(): void {
    if (this.selectedJob) {
      this.jobService.updateJobPosting(this.selectedJob.id, this.selectedJob).subscribe({
        next: () => {
          alert('Job updated successfully');
          this.cancelEdit();
          this.loadJobs();
        },
        error: err => {
          console.error('Error updating job:', err);
          alert('Failed to update job');
        }
      });
    }
  }

  cancelEdit(): void {
    this.editMode = false;
    this.selectedJob = null;
    document.body.style.overflow = 'auto';
  }
}