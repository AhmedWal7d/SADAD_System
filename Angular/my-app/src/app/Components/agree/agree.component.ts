import { Component, Input } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-agree',
  standalone: true,
  imports: [],
  templateUrl: './agree.component.html',
  styleUrls: ['./agree.component.css']
})
export class AgreeComponent {

  @Input() recordId!: number;

  private baseUrl = 'http://localhost:8080/api/sadad';
  private token = localStorage.getItem('token');

  loadingApprove = false;
  loadingCancel = false;

  constructor(private http: HttpClient, private toastr: ToastrService) {}

  cancel() {
    this.loadingCancel = true;
    this.http.put(`${this.baseUrl}/${this.recordId}/cancel`, {}, {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${this.token}`
      })
    }).subscribe({
      next: res => {
        this.toastr.success('Record cancelled successfully');
        console.log('Cancelled:', res);
      },
      error: err => {
        this.toastr.error('Failed to cancel record');
        console.error('Error:', err);
      },
      complete: () => this.loadingCancel = false
    });
  }

  approve() {
    this.loadingApprove = true;
    this.http.put(`${this.baseUrl}/${this.recordId}/approve`, {}, {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${this.token}`
      })
    }).subscribe({
      next: res => {
        this.toastr.success('Record approved successfully');
        console.log('Approved:', res);
      },
      error: err => {
        this.toastr.error('Failed to approve record');
        console.error('Error:', err);
      },
      complete: () => this.loadingApprove = false
    });
  }
}
