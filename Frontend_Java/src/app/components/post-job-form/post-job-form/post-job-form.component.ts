import { Component } from '@angular/core';
import { JobPost } from 'src/app/models/job-post.model';
import { JobService } from 'src/app/services/job.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-job-form',
  templateUrl: './post-job-form.component.html',
  styleUrls: ['./post-job-form.component.css']
})
export class PostJobFormComponent {
  jobPost: JobPost = new JobPost();
  isSubmitting: boolean = false;

  constructor(private jobService: JobService, private router: Router) {
    this.loadEmployerId();
  }

  loadEmployerId() {
    const employerIdStr = localStorage.getItem('employerId');

    if (employerIdStr) {
      this.jobPost.employerId = parseInt(employerIdStr, 10);
    } else {
      alert("Employer ID not found. Please complete your profile.");
      this.router.navigate(['/employer-profile']);
    }
  }

  postJob() {
    if (this.isSubmitting) return;

    this.isSubmitting = true;

    const deadline = new Date(this.jobPost.applicationDeadline);
    const today = new Date();

    today.setHours(0, 0, 0, 0);
    deadline.setHours(0, 0, 0, 0);

    if (deadline < today) {
      alert('Application deadline cannot be in the past. Please select a valid future date.');
      this.isSubmitting = false;
      return;
    }

    this.jobPost.applicationDeadline = deadline.toISOString().split('T')[0];

    this.jobService.postNewJob(this.jobPost).subscribe(
      {
        next: (res) => {
          this.isSubmitting = false;
          alert(res);
          this.router.navigate(['/my-posted-jobs']);
        },
        error: (error) => {
          this.isSubmitting = false;
          console.error(error);

          const backendMessage =
            error?.error && typeof error.error === 'string'
              ? error.error
              : 'An unexpected error occurred.';

          alert('Error posting job: ' + backendMessage);
        }
      });
  }

  cancelForm() {
    if (confirm('Are you sure you want to cancel? All unsaved changes will be lost.')) {
      this.router.navigate(['/post-job']);
    }
  }
}