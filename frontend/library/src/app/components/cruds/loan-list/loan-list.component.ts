import { Component, inject, OnInit } from '@angular/core';
import { LoanService } from '../../../services/loan.service';
import { Loan } from '../../../interfaces/loan';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-loan-list',
  standalone: true,
  imports: [RouterLink, MatButtonModule, CommonModule],
  templateUrl: './loan-list.component.html',
  styleUrls: ['./loan-list.component.css']
})
export class LoanListComponent implements OnInit {
  loanService = inject(LoanService);
  loans: Loan[] = [];

  constructor() {}

  ngOnInit(): void {
    this.loadLoans();
  }

  loadLoans() {
    this.loanService.getAllLoans().subscribe({
      next: (response) => {
        this.loans = response;
        console.log('Loans loaded:', this.loans);
      },
      error: (error) => {
        console.log('Error loading loans:', error);
      }
    });
  }
}
