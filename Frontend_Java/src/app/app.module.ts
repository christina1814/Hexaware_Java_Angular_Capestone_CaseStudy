import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login/login.component';
import { RegisterComponent } from './components/register/register/register.component';
import { EmployerHomeComponent } from './components/employer-home/employer-home/employer-home.component';
import { JobseekerHomeComponent } from './components/jobseeker-home/jobseeker-home/jobseeker-home.component';
import { NavbarComponent } from './components/navbar/navbar/navbar.component';
import { PostJobComponent } from './components/post-job/post-job/post-job.component';
import { PostJobFormComponent } from './components/post-job-form/post-job-form/post-job-form.component';
import { MyPostedJobsComponent } from './components/my-posted-jobs/my-posted-jobs/my-posted-jobs.component';
import { AllJobPostingsComponent } from './components/all-job-postings/all-job-postings/all-job-postings.component';
import { EmployerApplicationsComponent } from './components/employer-applications/employer-applications/employer-applications.component';
import { JobseekerJobsComponent } from './components/jobseeker-jobs/jobseeker-jobs/jobseeker-jobs.component';
import { JobseekerApplicationsComponent } from './components/jobseeker-applications/jobseeker-applications/jobseeker-applications.component';
import { HomeComponent } from './components/home/home/home.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    EmployerHomeComponent,
    JobseekerHomeComponent,
    NavbarComponent,
    PostJobComponent,
    PostJobFormComponent,
    MyPostedJobsComponent,
    AllJobPostingsComponent,
    EmployerApplicationsComponent,
    JobseekerJobsComponent,
    JobseekerApplicationsComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    RouterModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
