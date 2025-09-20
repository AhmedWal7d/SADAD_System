import { Component, Input } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { CommonModule, NgIf } from '@angular/common'; // ✅

@Component({
  selector: 'app-sadad-release',
  templateUrl: './sadad-release.component.html',
  standalone: true,
  imports: [CommonModule, NgIf]
})
export class SadadReleaseComponent {
  @Input() id!: number;
  result: boolean | null = null;
  error: string | null = null;

  private baseUrl = 'http://localhost:8080/api/sadad';

  constructor(private http: HttpClient) {}

  release(success: boolean) {
    if (!this.id) {
      this.error = 'Record ID is required';
      return;
    }

    const url = `${this.baseUrl}/${this.id}/release`;
    const params = new HttpParams().set('success', String(success));
    const token = localStorage.getItem('token');
    const headers = token ? new HttpHeaders().set('Authorization', `Bearer ${token}`) : undefined;

    this.http.put<any>(url, null, { params, headers }).subscribe({
      next: () => {
        this.result = success;
        this.error = null;
      },
      error: err => {
        this.error = err.error?.message || 'Error releasing record';
        this.result = null;
      }
    });
  }
}
