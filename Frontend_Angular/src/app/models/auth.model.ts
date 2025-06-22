export enum UserProfile 
{
  JOB_SEEKER = 'JOB_SEEKER',
  EMPLOYER = 'EMPLOYER'
}

export interface LoginRequest 
{
  email: string;
  password: string;
}

export interface RegisterRequest 
{
  fullName: string;
  email: string;
  password: string;
  userProfile: string;
}

export interface AuthResponse 
{
  token: string;
  role: string;
  userId: number;
}
