import { Component } from '@angular/core';
import { CommonModule, NgClass, NgFor } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { DialogModule } from "primeng/dialog";
import { ToastrService } from 'ngx-toastr';
import axios, { AxiosError } from 'axios';

@Component({
  selector: 'app-add-organization',
  standalone: true,
  imports: [DialogModule, FormsModule, NgClass, CommonModule, NgFor],
  templateUrl: './add-organization.component.html',
  styleUrls: ['./add-organization.component.css'],
})
export class AddOrganizationComponent {

  display = false;
  isLoading = false;
  organizations: any[] = [];

  organization: any = {
    locationName: '',
    // أضف أي حقول تانية هنا
  };

  private apiUrl = 'http://localhost:8080/api/sadad';

  constructor(private toastr: ToastrService) {}

  // 🔹 Lifecycle
  async ngOnInit() {
    await this.loadOrganizations();
  }

  // ===============================
  // 🔹 API Calls
  // ===============================

  private async loadOrganizations() {
    try {
      const token = localStorage.getItem('token');
      const res = await axios.get(this.apiUrl, {
        headers: { Authorization: `Bearer ${token}` }
      });
      this.organizations = res?.data || [];
    } catch (err) {
      this.handleError(err, 'Failed to fetch organizations');
    }
  }

  private async loadOrganizationById(id: string) {
    try {
      const token = localStorage.getItem('token');
      const res = await axios.get(`${this.apiUrl}/${id}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      this.fillForm(res.data);
    } catch (err) {
      this.handleError(err, 'Failed to load organization data');
    }
  }

  private async createOrganization(body: any, form: NgForm) {
    try {
      const token = localStorage.getItem('token');
      await axios.post(this.apiUrl, body, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`
        }
      });

      this.toastr.success('Created Successfully', 'Success');
      this.display = false;
      this.resetForm();
      form.resetForm();
    } catch (err) {
      this.handleError(err);
    } finally {
      this.isLoading = false;
    }
  }

  // ===============================
  // 🔹 Event Handlers
  // ===============================

  async onOrganizationChange(event: Event) {
    const selectElement = event.target as HTMLSelectElement;
    const id = selectElement.value;

    if (!id) return;

    if (id === 'new') {
      this.resetForm();
      return;
    }

    await this.loadOrganizationById(id);
  }

  async onSubmit(form: NgForm) {
    if (form.invalid) {
      console.warn('Form is invalid', form.form.value);
      this.toastr.warning('Please fill all required fields', 'Validation Error');
      return;
    }

    this.isLoading = true;

    const body = {
      ...form.form.value
    };

    await this.createOrganization(body, form);
  }

  showDialog() {
    this.display = true;
  }

  // ===============================
  // 🔹 Helpers
  // ===============================

  private resetForm() {
    this.organization = {
      locationName: '',
      // أضف أي حقول تانية هنا
    };
  }

  private fillForm(orgData: any) {
    this.organization = { ...orgData };
  }

  private handleError(error: any, fallbackMessage: string = 'Unexpected error') {
    console.error('Entered handleError function');
    console.error('Raw Error ', error);

    try {
      console.error('Error JSON:', JSON.stringify(error, null, 2));
    } catch {
      console.error('Error not serializable');
    }

    if (axios.isAxiosError(error)) {
      console.error('This is an AxiosError');
      const err = error as AxiosError<any>;
      console.error('Axios full error object:', err);
      console.error('Axios response:', err.response);
      console.error('Axios request:', err.request);

      const apiError = err.response?.data;

      if (apiError) {
        if (apiError.errors) {
          const messages = Array.isArray(apiError.errors)
            ? apiError.errors.join('\n')
            : Object.values(apiError.errors).join('\n');

          this.toastr.error(messages, 'Validation Error');
          return;
        }

        if (apiError.message) {
          this.toastr.error(apiError.message, 'API Error');
          return;
        }

        if (apiError.error) {
          this.toastr.error(apiError.error, 'API Error');
          return;
        }
      }

      this.toastr.error(err.message || fallbackMessage, 'Network/API Error');
      return;
    }

    console.error('Non-Axios Error detected');
    this.toastr.error(error?.message || fallbackMessage, 'Client Error');
  }
}
