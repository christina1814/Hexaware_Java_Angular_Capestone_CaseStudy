export class JobPost 
{
  id!: number;
  title!: string;
  description!: string;
  location!: string;
  jobType!: string;
  salaryRange?: string;
  applicationDeadline!: string; 
  qualifications?: string;
  employerId!: number; 
}
