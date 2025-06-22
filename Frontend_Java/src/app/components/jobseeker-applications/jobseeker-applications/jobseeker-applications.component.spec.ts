import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JobseekerApplicationsComponent } from './jobseeker-applications.component';

describe('JobseekerApplicationsComponent', () => {
  let component: JobseekerApplicationsComponent;
  let fixture: ComponentFixture<JobseekerApplicationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JobseekerApplicationsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JobseekerApplicationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
