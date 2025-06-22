import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JobSeekerProfile } from '../models/jobseeker-profile.model';

@Injectable({
  providedIn: 'root'
})
export class JobSeekerService {
  private BASE_URL = 'http://localhost:8181/api/jobseeker-profile';

  constructor(private http: HttpClient) { }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({ 'Authorization': `Bearer ${token}` });
  }

  getProfileByUserId(userId: number): Observable<JobSeekerProfile> {
    return this.http.get<JobSeekerProfile>(`${this.BASE_URL}/view/${userId}`,
      {
        headers: this.getAuthHeaders()
      });
  }

  updateProfileByJobSeekerId(jobseekerId: number, profile: any): Observable<string> {
    return this.http.put(`${this.BASE_URL}/update/${jobseekerId}`, profile,
      {
        headers: this.getAuthHeaders(),
        responseType: 'text'
      });
  }
}
