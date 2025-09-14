import { Component, Input } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-pending-status',
  standalone: true,
  imports: [],
  templateUrl: './pending-status.component.html',
  styleUrls: ['./pending-status.component.css']
})
export class PendingStatusComponent {
  @Input() recordId!: number;  
  constructor(private http: HttpClient, private toastr: ToastrService) {}
  displayDialog: boolean = false;

  markPending() {
    if (!this.recordId) {
      this.toastr.error('Record ID is missing ❌');
      return;
    }

    const token = localStorage.getItem('token'); 
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    this.http.put(
      `http://localhost:8080/api/sadad/${this.recordId}/mark-pending`, 
      {},
      { headers }
    ).subscribe({
      next: (res: any) => {
        const msg = res?.message || 'Record marked as Pending ✅';
        this.toastr.success(msg);
        console.log('Pending response:', res);
      },
      error: (err) => {
        const msg = err?.error?.error || 'Failed to mark record as Pending ❌';
        this.toastr.error(msg);
        console.error('Error:', err);
      }
    });

  }

    updatePop(record: any) {
    this.displayDialog = true;
  }
}
