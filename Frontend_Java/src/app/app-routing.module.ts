import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login/login.component';
import { RegisterComponent } from './components/register/register/register.component';
import { EmployerHomeComponent } from './components/employer-home/employer-home/employer-home.component';
import { JobseekerHomeComponent } from './components/jobseeker-home/jobseeker-home/jobseeker-home.component';
import { PostJobComponent } from './components/post-job/post-job/post-job.component';
import { PostJobFormComponent } from './components/post-job-form/post-job-form/post-job-form.component';
import { MyPostedJobsComponent } from './components/my-posted-jobs/my-posted-jobs/my-posted-jobs.component';
import { AllJobPostingsComponent } from './components/all-job-postings/all-job-postings/all-job-postings.component';
import { EmployerApplicationsComponent } from './components/employer-applications/employer-applications/employer-applications.component';
import { JobseekerJobsComponent } from './components/jobseeker-jobs/jobseeker-jobs/jobseeker-jobs.component';
import { JobseekerApplicationsComponent } from './components/jobseeker-applications/jobseeker-applications/jobseeker-applications.component';
import { HomeComponent } from './components/home/home/home.component';

const routes: Routes = [
  { path: '', component: HomeComponent }, // default route
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'employer-home', component: EmployerHomeComponent },
  { path: 'jobseeker-home', component: JobseekerHomeComponent },
  { path: 'post-job', component: PostJobComponent },
  { path: 'post-job-form', component: PostJobFormComponent },
  { path: 'my-posted-jobs', component: MyPostedJobsComponent },
  { path: 'all-job-postings', component: AllJobPostingsComponent },
  { path: 'employer-applications', component: EmployerApplicationsComponent },
  { path: 'jobseeker/jobs', component: JobseekerJobsComponent },
  {path: 'jobseeker/applications',component: JobseekerApplicationsComponent}

]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
