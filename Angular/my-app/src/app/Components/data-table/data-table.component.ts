import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { CommonModule, NgIf, NgClass } from '@angular/common';
import { Table, TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { SadadService } from '../../services/sadad.service';

import { AddOrganizationComponent } from "../add-organization/add-organization.component";
import { UpdateOrganizationComponent } from "../update-organization/update-organization.component";
import { PendingStatusComponent } from "../pending-status/pending-status.component";
import { AgreeComponent } from "../agree/agree.component";
import { LogoutComponent } from "../logout/logout.component";
import { UsersComponent } from "../users/users.component";
import { SadadReleaseComponent } from '../../sadad-release/sadad-release.component';

@Component({
  selector: 'app-data-table',
  standalone: true,
  imports: [
    CommonModule,
    NgIf,
    NgClass,
    TableModule,
    DialogModule,
    ButtonModule,
    DropdownModule,
    FormsModule,
    AddOrganizationComponent,
    UpdateOrganizationComponent,
    PendingStatusComponent,
    AgreeComponent,
    LogoutComponent,
    SadadReleaseComponent,
    UsersComponent
  ],
  templateUrl: './data-table.component.html',
  styleUrls: ['./data-table.component.css']
})
export class DataTableComponent implements OnInit {
  
  records: any[] = [];
  
  loading: boolean = true;

  displayDialog = false;
  displayDialogg = false;
  displayConfirme = false;
  displayDialoggg = false;
  canlepop = false;
  selectedId: number | null = null;
  selectedRecord: any = null;
  selectedRecorddd: any = null;
  selectedRecordd: any = null;
  selectedRecordForStatus: any = null;
showStatusDialog = false; 

  role: string | null = null;

  @Input() recordId!: number;
  @ViewChild('dt') dt!: Table;

  globalFilterValue: string = '';

  statusOptions = [
    { label: 'Approved', value: 'APPROVED' },
    { label: 'Pending', value: 'PENDING' },
    { label: 'Canceled', value: 'CANCELED' },
    { label: 'Saved', value: 'SAVED' },
    { label: 'Updated', value: 'UPDATED' }
  ];

  constructor(private sadadService: SadadService) {}
openDialog(record: any) {
  this.selectedRecordForStatus = record.id;
  console.log("WWWWWWWWWWWWWWWWW"  ,record.id);
  
  this.displayConfirme = true;
}
  openStatusDialog(record: any) {
    this.selectedId = record.id; 
        this.showStatusDialog = true;
  }
  ngOnInit(): void {
    this.role = localStorage.getItem('setroles')!;
    this.sadadService.getAll().subscribe({
      next: (data) => {
        this.records = data;
        this.loading = false;
        
      },
      error: (err) => {
        console.error('Error loading records:', err);
        this.loading = false;
      }
    });
  }

  viewRecord(record: any) {
    this.selectedRecord = record;
    this.displayDialog = true;
  }
  viewRecorddd(record: any) {
    this.selectedRecorddd = record;
    this.displayConfirme = true;
  }


  updatePop(record: any) {
    
    this.selectedRecordd = record;
    this.displayDialogg = true;
      console.log("Record in updatePop:", record);
  }
  
  changeStatus(record: any) {
    this.selectedRecordForStatus = record;
    this.displayDialoggg = true;
  }

cancleApprove(record: any) {
  console.log('Record passed to cancleApprove:', record.id);
  this.selectedRecordForStatus = record.id;
  this.canlepop = true;
}


  onGlobalFilter(event: Event) {
    const value = (event.target as HTMLInputElement).value;
    this.dt.filterGlobal(value, 'contains');
  }
}
