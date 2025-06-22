export interface ApplicationSummary 
{
  applicationId: number;
  jobTitle: string;
  seekerEmail: string;
  status: string;
}

export interface JobseekerApplication 
{
  applicationId: number;
  jobId: number;
  jobTitle: string;
  companyName: string;
  status: string;
}
