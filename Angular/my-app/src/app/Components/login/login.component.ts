import { NgClass, NgFor, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import axios from 'axios';
import { ToastrService } from 'ngx-toastr';
import { DialogModule } from 'primeng/dialog';
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, NgIf, NgFor, NgClass, DialogModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  showPassword = false;
  isLoading = false;

  constructor(private toastr: ToastrService, private router: Router) {}

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  onSubmit(form: NgForm) {
    if (form.valid) {
      const { username, password } = form.value;
      this.isLoading = true;
      axios
        .post('http://localhost:8080/api/auth/login', {
          username: username,
          password: password,
        })
        .then((response) => {
          localStorage.setItem('token', response?.data?.token);
          this.router.navigate(['/dashboard']);
          
          localStorage.setItem('setroles', response.data?.roles[0]);
          this.toastr.success('Login successful!', 'Success');
        })
        .catch(() => {
          this.toastr.error('Invalid username or password.', 'Login Failed');
        })
        .finally(() => {
          this.isLoading = false;
        });
    } else {
      console.log('Form is invalid');
      this.toastr.warning('Please fill out all required fields.', 'Warning');
    }
  }
}
