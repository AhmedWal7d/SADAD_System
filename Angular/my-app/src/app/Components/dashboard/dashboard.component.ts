import { Component } from '@angular/core';
import { DataTableComponent } from "../data-table/data-table.component";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [DataTableComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

}
