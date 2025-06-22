import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationSummary, JobseekerApplication } from '../models/application-response.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApplicationService {

  private baseUrl = 'http://localhost:8181/api/applications';

  constructor(private http: HttpClient) { }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders(
      { Authorization: `Bearer ${token}` }
    );
  }

  applyToJob(data: { jobId: number, seekerId: number, resumePath: string }): Observable<string> {
    return this.http.post<string>(
      `${this.baseUrl}/apply`, data ,
      { headers: this.getAuthHeaders(), responseType: 'text' as 'json' }
    );
  }


  getApplicationsByJob(jobId: number): Observable<ApplicationSummary[]> {
    return this.http.get<ApplicationSummary[]>(
      `${this.baseUrl}/job/${jobId}`,
      { headers: this.getAuthHeaders() }
    );
  }

  updateApplicationStatus(applicationId: number, newStatus: string): Observable<string> {
    return this.http.put<string>(`${this.baseUrl}/${applicationId}/status?status=${newStatus}`, {},
      { headers: this.getAuthHeaders(), responseType: 'text' as 'json' }
    );
  }

  getApplicationsForJobseeker(seekerId: number): Observable<JobseekerApplication[]> {
    return this.http.get<JobseekerApplication[]>(
      `${this.baseUrl}/seeker/${seekerId}`,
      { headers: this.getAuthHeaders() }
    );
  }

  withdrawApplication(applicationId: number): Observable<string> {
    return this.http.delete<string>(
      `${this.baseUrl}/withDraw/${applicationId}`,
      { headers: this.getAuthHeaders(), responseType: 'text' as 'json' }
    );
  }


}
