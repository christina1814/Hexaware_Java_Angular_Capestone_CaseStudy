import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JobPost } from 'src/app/models/job-post.model';

@Injectable({ providedIn: 'root' })
export class JobService {
  private baseUrl = 'http://localhost:8181/api/job-postings';

  constructor(private http: HttpClient) { }

  private getAuthHeaders(): HttpHeaders 
  {
    const token = localStorage.getItem('token');
    return new HttpHeaders({'Authorization': `Bearer ${token}` });
  }

  getJobsByEmployer(employerId: number): Observable<JobPost[]> 
  {
    return this.http.get<JobPost[]>(`${this.baseUrl}/employer/${employerId}`, 
    {
      headers: this.getAuthHeaders()
    });
  }

  postNewJob(job: JobPost): Observable<any> 
  {
    return this.http.post(`${this.baseUrl}/add`, job, 
    {
      headers: this.getAuthHeaders(),
      responseType: 'text' as 'json' 
    });
  }


  getAllJobs(): Observable<JobPost[]> 
  {
    return this.http.get<JobPost[]>(`${this.baseUrl}/search`,
    {
      headers: this.getAuthHeaders() 
    });
  }

  updateJobPosting(id: number, job: JobPost): Observable<any> 
  {
    return this.http.put(`${this.baseUrl}/update/${id}`, job, 
    {
      headers: this.getAuthHeaders(),
      responseType: 'text' as 'json'
    });
  }

  deleteJobPosting(id: number): Observable<any> 
  {
    return this.http.delete(`${this.baseUrl}/remove/${id}`, 
    {
      headers: this.getAuthHeaders(),
      responseType: 'text'
    });

  }

}
