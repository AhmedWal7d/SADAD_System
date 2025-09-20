import { CommonModule, NgClass, NgFor } from '@angular/common';
import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { DialogModule } from "primeng/dialog";
import { ToastrService } from 'ngx-toastr';
import axios, { AxiosError } from 'axios';

@Component({
  selector: 'app-update-organization',
  standalone: true,
  imports: [DialogModule, FormsModule, NgClass, NgFor, CommonModule],
  templateUrl: './update-organization.component.html',
  styleUrls: ['./update-organization.component.css']
})
export class UpdateOrganizationComponent implements OnChanges {
  display: boolean = false;
  isLoading = false;
    @Input() record: any;

  @Input() recordId!: number;

  formData: any = {
    organizationCode: '',
    organizationName: '',
    legalEntity: '',
    remitterBank: '',
    remitterBankAccount: '',
    remitterBankAccountName: '',
    billerId: '',
    billerName: '',
    vendorNumber: '',
    vendorName: '',
    vendorSiteCode: '',
    invoiceType: '',
    invoiceNumber: '',
    subscriptionAccountNumber: '',
    amount: 0,
    expenseAccountCode: '',
    expenseAccountName: '',
    businessCode: '',
    businessName: '',
    locationCode: '',
    locationName: ''
  };

  private apiUrl = 'http://localhost:8080/api/sadad';

  constructor(private toastr: ToastrService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['recordId'] && this.recordId) {
      this.fetchRecord();
    }
  }

  async fetchRecord() {
    const token = localStorage.getItem('token');
    try {
      const res = await axios.get(`${this.apiUrl}/${this.recordId}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      this.formData = res.data;
      this.display = true;
    } catch (err) {
      this.handleError(err, 'Error loading record');
    }
  }

  async onSubmit(form: NgForm) {
    if (form.invalid) {
      this.toastr.warning('Please fill all required fields', 'Validation Error');
      return;
    }

    this.isLoading = true;
    const token = localStorage.getItem('token');

    try {
      await axios.put(`${this.apiUrl}/${this.recordId}`, this.formData, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`
        }
      });
      this.toastr.success('Updated Successfully ✅', 'Success');
      this.display = false;
      form.resetForm();
      this.resetForm();
    } catch (err) {
      this.handleError(err, 'Update failed');
    } finally {
      this.isLoading = false;
    }
  }

  showDialog() {
    this.display = true;
  }

  private resetForm() {
    this.formData = {
      organizationCode: '',
      organizationName: '',
      legalEntity: '',
      remitterBank: '',
      remitterBankAccount: '',
      remitterBankAccountName: '',
      billerId: '',
      billerName: '',
      vendorNumber: '',
      vendorName: '',
      vendorSiteCode: '',
      invoiceType: '',
      invoiceNumber: '',
      subscriptionAccountNumber: '',
      amount: 0,
      expenseAccountCode: '',
      expenseAccountName: '',
      businessCode: '',
      businessName: '',
      locationCode: '',
      locationName: ''
    };
  }

  private handleError(error: any, fallbackMessage: string = 'Unexpected error') {
    console.error('Error:', error);
    if (axios.isAxiosError(error)) {
      const err = error as AxiosError<any>;
      const apiError = err.response?.data;
      if (apiError?.message) this.toastr.error(apiError.message, 'API Error');
      else this.toastr.error(err.message || fallbackMessage, 'Network/API Error');
      return;
    }
    this.toastr.error(error?.message || fallbackMessage, 'Client Error');
  }
}
