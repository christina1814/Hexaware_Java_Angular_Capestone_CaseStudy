import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EmployerProfile } from 'src/app/models/employer-profile.model';

@Injectable({
  providedIn: 'root'
})
export class EmployerService {
  private BASE_URL = 'http://localhost:8181/api/employer-profile';

  constructor(private http: HttpClient) { }

  private getAuthHeaders(): HttpHeaders 
  {
    const token = localStorage.getItem('token');
    return new HttpHeaders({'Authorization': `Bearer ${token}`});
  }

  getProfileByUserId(userId: number): Observable<EmployerProfile> 
  {
    return this.http.get<EmployerProfile>(
      `${this.BASE_URL}/view/${userId}`,
      { headers: this.getAuthHeaders() }
    );
  }

  updateProfileByUserId(userId: number, profile: EmployerProfile): Observable<string> 
  {
    return this.http.put<string>(`${this.BASE_URL}/update/${userId}`, profile,
      {
        headers: this.getAuthHeaders().set('Content-Type', 'application/json'),
        responseType: 'text' as 'json'
      }
    );
  }

}
