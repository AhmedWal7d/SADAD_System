import { Component } from '@angular/core';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { NgIf, NgFor } from '@angular/common';
import { ToastrService } from 'ngx-toastr';
import axios from 'axios';
import { TableModule } from "primeng/table";
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService } from 'primeng/api';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [
    DialogModule,
    ButtonModule,
    DropdownModule,
    FormsModule,
    NgIf,
    NgFor,
    TableModule,
    ConfirmDialogModule
  ],
  providers: [ConfirmationService],
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent {
  // -------------------------------
  // UI State
  // -------------------------------
  displayDialog: boolean = false;
  canlepop: boolean = false;
selectedUserId: number | null = null;
  // Form Data
  username: string = '';
  password: string = '';
  role: string = '';

  // Dropdown Options
  roles = [
    { label: 'Entry', value: 'ROLE_ENTRY' },
    { label: 'Approver', value: 'ROLE_APPROVER' },
    { label: 'Releaser', value: 'ROLE_RELEASER' }
  ];


  alluser: any[] = [];

  constructor(
    private toastr: ToastrService,
    private confirmationService: ConfirmationService
  ) {}

  // -------------------------------
  // Lifecycle
  // -------------------------------
  ngOnInit(): void {
    this.getUsers();
  }

  // -------------------------------
  // Dialog Control
  // -------------------------------
  showDialog() {
    this.displayDialog = true;
  }

  // -------------------------------
  // CRUD Operations
  // -------------------------------


  async addUser() {
    const token = localStorage.getItem('token');
    const body = {
      username: this.username,
      password: this.password,
      role: this.role
    };

    try {
      await axios.post('http://localhost:8080/api/auth/register', body, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });

      this.toastr.success('User added successfully!', 'Success');
      this.displayDialog = false;

     
      this.username = '';
      this.password = '';
      this.role = '';

      this.getUsers(); 
    } catch (err: any) {
      console.error('Error adding user:', err);
      this.toastr.error('Failed to add user', 'Error');
    }
  }

  async getUsers() {
    const token = localStorage.getItem('token');
    try {
      const res = await axios.get("http://localhost:8080/api/users", {
        headers: { 'Authorization': `Bearer ${token}` }
      });
      this.alluser = res.data;
    } catch (err) {
      console.error('Error fetching users:', err);
      this.toastr.error('Failed to load users', 'Error');
    }
  }


openDeleteDialog(userId: number) {
  this.selectedUserId = userId;
  this.canlepop = true;
}

confirmDeleteUser() {
  if (this.selectedUserId !== null) {
    this.deleteUser(this.selectedUserId);
    this.selectedUserId = null;
  }
  this.canlepop = false;
}


  async deleteUser(userId: number) {
    const token = localStorage.getItem('token');
    try {
      await axios.delete(`http://localhost:8080/api/users/${userId}`, {
        headers: { 'Authorization': `Bearer ${token}` }
      });

      this.toastr.success('User deleted successfully!', 'Success');
      this.getUsers();
    } catch (err) {
      console.error('Error deleting user:', err);
      this.toastr.error('Failed to delete user', 'Error');
    }
  }
}
