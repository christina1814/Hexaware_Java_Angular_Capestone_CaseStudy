import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  fullName = '';
  email = '';
  password = '';
  userProfile = '';
  isLoading = false;
  passwordStrength = 0;
  passwordStrengthText = '';
  passwordStrengthClass = '';

  constructor(private authService: AuthService, private router: Router) { }

  checkPasswordStrength() {
    if (!this.password) {
      this.passwordStrength = 0;
      return;
    }

    let strength = 0;
    
    // Length check
    if (this.password.length > 5) strength += 30;
    if (this.password.length > 8) strength += 20;
    
    // Complexity checks
    if (/[A-Z]/.test(this.password)) strength += 15;
    if (/[0-9]/.test(this.password)) strength += 15;
    if (/[^A-Za-z0-9]/.test(this.password)) strength += 20;
    
    this.passwordStrength = Math.min(100, strength);

    if (this.passwordStrength < 40) {
      this.passwordStrengthText = 'Weak';
      this.passwordStrengthClass = 'password-weak';
    } else if (this.passwordStrength < 70) {
      this.passwordStrengthText = 'Medium';
      this.passwordStrengthClass = 'password-medium';
    } else {
      this.passwordStrengthText = 'Strong';
      this.passwordStrengthClass = 'password-strong';
    }
  }

  onRegister() {
    this.isLoading = true;
    
    this.authService.register({
      fullName: this.fullName,
      email: this.email,
      password: this.password,
      userProfile: this.userProfile
    }).subscribe({
      next: () => {
        this.showSuccessAlert('Registration successful! Redirecting to login...');
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 1500);
      },
      error: (error: any) => {
        this.isLoading = false;
        if (error?.error && typeof error.error === 'string') {
          this.showAccountExistsAlert(error.error);
        } else {
          this.showErrorAlert('Registration failed. Please try again.');
        }
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
      showConfirmButton: false,
      background: 'rgba(255, 255, 255, 0.9)',
      backdrop: `
        rgba(102, 126, 234, 0.4)
      `
    });
  }

  private showErrorAlert(message: string) {
    Swal.fire({
      title: 'Error!',
      text: message,
      icon: 'error',
      confirmButtonColor: '#764ba2',
      confirmButtonText: 'Try Again',
      background: 'rgba(255, 255, 255, 0.9)',
      backdrop: `
        rgba(102, 126, 234, 0.4)
      `
    });
  }

  private showAccountExistsAlert(message: string) {
    Swal.fire({
      title: 'Account Exists',
      text: message,
      icon: 'info',
      showCancelButton: true,
      confirmButtonColor: '#764ba2',
      cancelButtonColor: '#6c757d',
      confirmButtonText: 'Go to Login',
      cancelButtonText: 'Stay Here',
      background: 'rgba(255, 255, 255, 0.9)',
      backdrop: `
        rgba(102, 126, 234, 0.4)
      `
    }).then((result) => {
      if (result.isConfirmed) {
        this.router.navigate(['/login']);
      }
    });
  }
}