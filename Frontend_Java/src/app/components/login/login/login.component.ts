import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email = '';
  password = '';
  isLoading = false;

  constructor(private authService: AuthService, private router: Router) { }

  onLogin() {
    this.isLoading = true;
    
    this.authService.login({ email: this.email, password: this.password }).subscribe({
      next: (res) => {
        this.authService.saveAuthData(res.token, res.role, res.userId);
        this.showSuccessAlert('Login successful! Redirecting...');
        
        setTimeout(() => {
          if (res.role === 'EMPLOYER') {
            this.router.navigate(['/employer-home']);
          } else {
            this.router.navigate(['/jobseeker-home']);
          }
        }, 1500);
      },
      error: (error: any) => {
        this.isLoading = false;
        if (error?.error && typeof error.error === 'string') {
          this.showErrorAlert(error.error);
        } else {
          this.showErrorAlert('Login failed. Please check your credentials and try again.');
        }
      },
      complete: () => {
        this.isLoading = false;
      }
    });
  }

  private showSuccessAlert(message: string) {
    Swal.fire({
      title: 'Success!',
      text: message,
      icon: 'success',
      confirmButtonColor: '#764ba2',
      timer: 1500,
      showConfirmButton: false
    });
  }

  private showErrorAlert(message: string) {
    Swal.fire({
      title: 'Error!',
      text: message,
      icon: 'error',
      confirmButtonColor: '#764ba2',
      confirmButtonText: 'Try Again'
    });
  }
}