import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginRequest, RegisterRequest, AuthResponse } from '../models/auth.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private BASE_URL = 'http://localhost:8181/api/auth';

  constructor(private http: HttpClient) { }

  login(data: LoginRequest): Observable<AuthResponse> 
  {
    return this.http.post<AuthResponse>(`${this.BASE_URL}/login`, data);
  }

  register(data: RegisterRequest): Observable<string> 
  {
    return this.http.post(`${this.BASE_URL}/register`, data, { responseType: 'text' });
  }

  saveAuthData(token: string, role: string, userId: number) 
  {
    localStorage.setItem('token', token);
    localStorage.setItem('role', role);
    localStorage.setItem('userId', userId.toString());
  }

  getToken(): string | null 
  {
    return localStorage.getItem('token');
  }

  getRole(): string | null 
  {
    return localStorage.getItem('role');
  }

  getUserId(): number | null 
  {
    const id = localStorage.getItem('userId');
    return id ? +id : null;
  }

  logout(): void 
  {
    localStorage.clear();
  }
}
