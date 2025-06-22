import { Component, OnInit } from '@angular/core';
import { JobService } from 'src/app/services/job.service';
import { JobPost } from 'src/app/models/job-post.model';

@Component({
  selector: 'app-all-job-postings',
  templateUrl: './all-job-postings.component.html',
  styleUrls: ['./all-job-postings.component.css']
})
export class AllJobPostingsComponent implements OnInit {
  allJobs: JobPost[] = [];
  loadingJobs: boolean = true;
  colorPalette = ['#667eea', '#764ba2', '#6B8DD6', '#8E37D7', '#5B7DBF'];

  constructor(private jobService: JobService) {}

  ngOnInit() {
    this.jobService.getAllJobs().subscribe({
      next: data => this.allJobs = data,
      error: err => console.error('Error fetching jobs:', err),
      complete: () => this.loadingJobs = false
    });
  }

  getRandomColor(): string {
    return this.colorPalette[Math.floor(Math.random() * this.colorPalette.length)];
  }
}