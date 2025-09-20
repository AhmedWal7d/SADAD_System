import { Component, OnInit } from '@angular/core';
import { CommonModule, NgClass, NgFor, NgIf } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { DialogModule } from 'primeng/dialog';
import { ToastrService } from 'ngx-toastr';
import axios from 'axios';

@Component({
  selector: 'app-add-organization',
  standalone: true,
  imports: [DialogModule, FormsModule, NgClass, CommonModule, NgFor, NgIf],
  templateUrl: './add-organization.component.html',
  styleUrls: ['./add-organization.component.css']
})
export class AddOrganizationComponent implements OnInit {
  display = false;
  isLoading = false;

  organizations: Array<{ code: string, name: string }> = [];
  legalEntities: Array<{ code: string, name: string }> = [];
  remitterBanks: Array<{ code: string, name: string }> = [];
  remitterBankAccounts: Array<{ code: string, name: string }> = [];
  billers: Array<{ code: string, name: string }> = [];
  vendors: Array<{ code: string, name: string }> = [];
  vendorSites: Array<{ code: string, name: string }> = [];
  expenseAccounts: Array<{ code: string, name: string }> = [];
  businesses: Array<{ code: string, name: string }> = [];
  locations: Array<{ code: string, name: string }> = [];
  costCenters: Array<{ code: string, name: string }> = [];

  invoiceTypes = ['Utility', 'Services', 'Other'];

  organization: any = this.emptyOrganization();

  private apiUrl = 'http://localhost:8080/api/lov';

  constructor(private toastr: ToastrService) {}

  async ngOnInit(): Promise<void> {
    const endpoints = [
      { endpoint: 'organizations', target: 'organizations' },
      { endpoint: 'legal-entities', target: 'legalEntities' },
      { endpoint: 'billers', target: 'billers' },
      { endpoint: 'expense-accounts', target: 'expenseAccounts' },
      { endpoint: 'business-units', target: 'businesses' },
      { endpoint: 'locations', target: 'locations' },
      { endpoint: 'cost-centers', target: 'costCenters' }
    ];

    // Load all basic lists in parallel
    await Promise.all(endpoints.map(e => this.loadData(e.endpoint, e.target)));

    // vendor sites only load after user selects a vendor. (Remove fixed loading)
    // If you want a default vendorSites list for some default vendor, call loadVendorSites('VE001') here.
  }

  private emptyOrganization() {
    return {
      organizationCode: '',
      organizationName: '',
      legalEntity: '',
      billerId: '',
      billerName: '',
      remitterBank: '',
      remitterBankAccount: '',
      remitterBankAccountName: '',
      vendorNumber: '',
      vendorName: '',
      vendorSiteCode: '',
      invoiceType: '',
      invoiceNumber: '',
      subscriptionAccountNumber: '',
      amount: null,
      expenseAccountCode: '',
      expenseAccountName: '',
      businessCode: '',
      businessName: '',
      locationCode: '',
      locationName: '',
      costCenters: [] as Array<{ costCenterCode: string, costCenterDesc: string, percentage: number }>
    };
  }

  private async loadData(endpoint: string, target: string) {
    try {
      const token = localStorage.getItem('token');
      const res = await axios.get(`${this.apiUrl}/${endpoint}`, {
        headers: token ? { Authorization: `Bearer ${token}` } : {}
      });

      let raw = res.data?.data ?? res.data ?? [];
      if (!Array.isArray(raw)) raw = [];

      const normalized = raw.map((it: any) => {
        const code = it.code ?? it.id ?? it.organizationCode ?? it.vendorNumber ?? it.billerId ?? it.accountCode ?? it.expenseAccountCode ?? it.businessCode ?? it.locationCode ?? it.costCenterCode ?? '';
        const name = it.name ?? it.organizationName ?? it.vendorName ?? it.accountName ?? it.billerName ?? it.expenseAccountName ?? it.businessName ?? it.locationName ?? it.costCenterDesc ?? it.description ?? '';
        return { code: String(code), name: String(name) };
      });

      (this as any)[target] = normalized;
      console.debug(`Loaded ${target}`, (this as any)[target]);
    } catch (err) {
      console.error(`Failed to load ${target}`, err);
      this.toastr.error(`Failed to load ${target}`);
      (this as any)[target] = [];
    }
  }

  // Organization select
  onOrganizationChange(event: any) {
    const orgCode = (event.target as HTMLSelectElement).value;
    this.organization.organizationCode = orgCode;

    const org = this.organizations.find(o => o.code === orgCode);
    this.organization.organizationName = org?.name ?? '';

    this.vendors = [];
    this.remitterBanks = [];
    this.remitterBankAccounts = [];
    this.vendorSites = [];
    this.organization.vendorNumber = '';
    this.organization.vendorName = '';

    if (orgCode) {
      this.loadSuppliers(orgCode);
      this.loadBanksByOrganization(orgCode);
    }
  }

  // Biller select (wired to template)
  onBillerChange(event: any) {
    const billerId = (typeof event === 'string') ? event : event.target?.value;
    this.organization.billerId = billerId;
    const biller = this.billers.find(b => b.code === billerId);
    this.organization.billerName = biller?.name ?? '';

    // reset vendors/vendor sites until new load
    this.vendors = [];
    this.vendorSites = [];
    this.organization.vendorNumber = '';
    this.organization.vendorName = '';

    if (billerId) {
      this.loadVendors(billerId);
    }
  }

  async loadVendors(billerId: string) {
    try {
      const token = localStorage.getItem('token');
      const res = await axios.get(`${this.apiUrl}/vendors`, {
        params: { billerId },
        headers: token ? { Authorization: `Bearer ${token}` } : {}
      });

      let raw = res.data?.data ?? res.data ?? [];
      if (!Array.isArray(raw)) raw = [];

      this.vendors = raw.map((v: any) => ({
        code: v.code ?? v.vendorNumber ?? '',
        name: v.name ?? v.vendorName ?? ''
      }));

      console.debug('Loaded vendors for biller', billerId, this.vendors);
    } catch (err) {
      console.error('Failed to load vendors', err);
      this.toastr.error('Failed to load vendors');
      this.vendors = [];
    }
  }

  // Vendor change
onVendorChange(event: any) {
  const vendorNumber = (typeof event === 'string') ? event : event.target?.value;
  console.log('📌 Selected Vendor Number:', vendorNumber);

  this.organization.vendorNumber = vendorNumber;

  const vendor = this.vendors.find(v => v.code === vendorNumber);
  this.organization.vendorName = vendor ? vendor.name : '';
  console.log('📌 Selected Vendor Object:', vendor);

  if (vendorNumber) {
    this.loadVendorSites(vendorNumber);
  } else {
    this.vendorSites = [];
    this.organization.vendorSiteCode = '';
  }
}



async loadVendorSites(vendorCode: string) {
  try {
    console.log('➡️ Calling API /vendor-sites with vendorCode:', vendorCode);
    const token = localStorage.getItem('token');
    const res = await axios.get(`${this.apiUrl}/vendor-sites`, {
      params: { vendorNumber: vendorCode },
      headers: token ? { Authorization: `Bearer ${token}` } : {}
    });

    console.log('⬅️ API Response /vendor-sites:', res);

    let raw = res.data ?? [];
    if (!Array.isArray(raw)) {
      raw = [raw];
    }

this.vendorSites = raw.map((vs: any) => ({
  code: vs.code ?? '',
  name: vs.name ?? vs.code ?? ''
}));


    console.log('✅ Normalized Vendor Sites:', this.vendorSites);
  } catch (err) {
    console.error('❌ Failed to load vendor sites', err);
    this.toastr.error('Failed to load vendor sites');
    this.vendorSites = [];
  }
}



  onVendorSiteChange(event: any) {
    const siteCode = (typeof event === 'string') ? event : (event.target as HTMLSelectElement).value;
    this.organization.vendorSiteCode = siteCode;
  }

  // Banks
  async loadBanksByOrganization(orgCode: string) {
    try {
      const token = localStorage.getItem('token');
      const res = await axios.get(`${this.apiUrl}/banks`, {
        params: { organizationCode: orgCode },
        headers: token ? { Authorization: `Bearer ${token}` } : {}
      });

      let raw = res.data?.data ?? res.data ?? [];
      if (!Array.isArray(raw)) raw = [];

      this.remitterBanks = raw.map((b: any) => ({
        code: b.code ?? b.bankCode ?? '',
        name: b.name ?? b.bankName ?? ''
      }));
    } catch (err) {
      console.error('Failed to fetch banks', err);
      this.toastr.error('Failed to fetch banks');
      this.remitterBanks = [];
    }
  }

  onRemitterBankChange(event: any) {
    const bankCode = (typeof event === 'string') ? event : (event.target as HTMLSelectElement).value;
    this.organization.remitterBank = bankCode;

    this.remitterBankAccounts = [];
    this.organization.remitterBankAccount = '';
    this.organization.remitterBankAccountName = '';

    if (bankCode) {
      this.loadBankAccounts(bankCode);
    }
  }

  async loadBankAccounts(bankCode: string) {
    try {
      const token = localStorage.getItem('token');
      // <-- FIXED endpoint: bank-accounts (not expense-accounts)
      const res = await axios.get(`${this.apiUrl}/bank-accounts`, {
        params: { bankCode },
        headers: token ? { Authorization: `Bearer ${token}` } : {}
      });

      let raw = res.data?.data ?? res.data ?? [];
      if (!Array.isArray(raw)) raw = [];

      this.remitterBankAccounts = raw.map((acc: any) => ({
        code: acc.code ?? acc.accountNumber ?? '',
        name: acc.name ?? acc.accountName ?? ''
      }));
    } catch (err) {
      console.error('Failed to fetch bank accounts', err);
      this.toastr.error('Failed to fetch bank accounts');
      this.remitterBankAccounts = [];
    }
  }

  onRemitterBankAccountChange(event: any) {
    const accountNumber = (typeof event === 'string') ? event : (event.target as HTMLSelectElement).value;
    this.organization.remitterBankAccount = accountNumber;

    const account = this.remitterBankAccounts.find(a => a.code === accountNumber);
    if (account) {
      this.organization.remitterBankAccountName = account.name;
    } else if (accountNumber) {
      // fallback: fetch by API
      this.loadBankAccountName(accountNumber);
    } else {
      this.organization.remitterBankAccountName = '';
    }
  }

  async loadBankAccountName(accountNumber: string) {
    try {
      const token = localStorage.getItem('token');
      const res = await axios.get(`${this.apiUrl}/bank-account-name`, {
        params: { accountNumber },
        headers: token ? { Authorization: `Bearer ${token}` } : {}
      });

      const raw = res.data ?? {};
      this.organization.remitterBankAccountName = raw.name ?? raw.accountName ?? raw.description ?? '';
    } catch (err) {
      console.error('Failed to fetch bank account name', err);
      this.toastr.error('Failed to fetch bank account name');
      this.organization.remitterBankAccountName = '';
    }
  }

  // Suppliers by organization
  async loadSuppliers(orgCode: string) {
    try {
      const token = localStorage.getItem('token');
      const res = await axios.get(`${this.apiUrl}/suppliers`, {
        params: { organizationCode: orgCode },
        headers: token ? { Authorization: `Bearer ${token}` } : {}
      });

      let raw = res.data?.data ?? res.data ?? [];
      if (!Array.isArray(raw)) raw = [];

      this.vendors = raw.map((v: any) => ({
        code: v.code ?? v.vendorNumber ?? '',
        name: v.name ?? v.vendorName ?? ''
      }));
    } catch (err) {
      console.error('Failed to load suppliers', err);
      this.toastr.error('Failed to load suppliers');
      this.vendors = [];
    }
  }

  // Generic select handler for expense / business / location
  onGenericSelect(event: any, list: any[], codeField: string, nameField: string) {
    const selectedCode = (typeof event === 'string') ? event : (event.target as HTMLSelectElement).value;
    const selectedItem = list.find(item => item.code === selectedCode);

    if (selectedItem) {
      (this.organization as any)[codeField] = selectedItem.code;
      (this.organization as any)[nameField] = selectedItem.name;
    } else {
      (this.organization as any)[codeField] = '';
      (this.organization as any)[nameField] = '';
    }
  }

  // Cost centers
  onCostCenterChange(event: Event, index: number) {
    const code = (event.target as HTMLSelectElement).value;
    const cc = this.costCenters.find(c => c.code === code);
    this.organization.costCenters[index].costCenterDesc = cc?.name ?? '';
  }

  // Dialog open
  showDialog() {
    this.organization = this.emptyOrganization();
    this.display = true;
  }

  // Submit
  onSubmit(form: NgForm) {
    if (form.invalid) {
      this.toastr.warning('Please fill all required fields');
      return;
    }

    const totalPercentage = this.organization.costCenters.reduce(
      (sum: number, cc: any) => sum + Number(cc.percentage || 0),
      0
    );
    if (totalPercentage !== 100) {
      this.toastr.warning('Total cost center percentage must be 100%');
      return;
    }

    this.isLoading = true;
    axios.post('http://localhost:8080/api/sadad', this.organization, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    })
    .then(() => {
      this.toastr.success('SADAD record saved');
      this.display = false;
      form.resetForm();
      this.resetForm();
    })
    .catch(err => {
      console.error('Failed to save SADAD', err);
      this.toastr.error('Failed to save SADAD');
    })
    .finally(() => this.isLoading = false);
  }

  private resetForm() {
    this.organization = this.emptyOrganization();
    this.remitterBankAccounts = [];
    this.vendors = [];
    this.vendorSites = [];
  }
duplicateRecord(record: any) {
  this.organization = JSON.parse(JSON.stringify(record)); // نسخة جديدة
  this.organization.id = null; // إزالة الـ id علشان السيرفر يعمل واحد جديد
  this.display = true;
}

}
