import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { SadadService } from '../../services/sadad.service';
import { AddOrganizationComponent } from "../add-organization/add-organization.component";
import { UpdateOrganizationComponent } from "../update-organization/update-organization.component";
import { PendingStatusComponent } from "../pending-status/pending-status.component";
import { AgreeComponent } from "../agree/agree.component";
import { LogoutComponent } from "../logout/logout.component";
import { UsersComponent } from "../users/users.component";

@Component({
  selector: 'app-data-table',
  standalone: true,
  imports: [CommonModule, TableModule, DialogModule, ButtonModule, AddOrganizationComponent, UpdateOrganizationComponent, PendingStatusComponent, AgreeComponent, LogoutComponent, UsersComponent],
  templateUrl: './data-table.component.html',
  styleUrls: ['./data-table.component.css']
})
export class DataTableComponent implements OnInit {
  records: any[] = [];  
  loading: boolean = true;

  displayDialog: boolean = false;
  displayDialogg: boolean = false;
  displayDialoggg: boolean = false;
  canlepop: boolean = false;
  selectedRecord: any = null;
  selectedRecordd: any = null;
  selectedRecorddd: any = null;
  role: string | null = null;
  @Input() recordId!: number;  

selectedRecordForStatus: any = null;


  constructor(private sadadService: SadadService) {}

  ngOnInit(): void {

      this.role = localStorage.getItem('setroles')!;

    this.sadadService.getAll().subscribe({
      next: (data) => {
        this.records = data;
        this.loading = false;
        console.log(data)
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
  updatePop(record: any) {
    this.selectedRecordd = record;
    this.displayDialogg = true;
  }
changeStatus(record: any) {
  this.selectedRecordForStatus = record;
  this.displayDialoggg = true;
}
cancleApprove(record: any) {
  this.selectedRecordForStatus = record;
  this.canlepop = true;
}

}
