import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';
import { Loan } from '../interfaces/loan'; 
import { HttpHeaders } from '@angular/common/http';

const API_URL = `${environment.apiURL}/loans`;  


@Injectable({
  providedIn: 'root'
})
export class LoanService {
  http: HttpClient = inject(HttpClient);

  constructor() {}

  
  saveLoan(loan: Loan) {
    const token = localStorage.getItem('token');
    let headers = new HttpHeaders();

    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }

    return this.http.post<{msg: string}>(`${API_URL}/save`, loan, { headers });
  }

  // Μέθοδος για να πάρουμε όλα τα δάνεια
  getAllLoans(filters?: Loan) {
    const token = localStorage.getItem('token');
    let headers = new HttpHeaders();

    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }

    if (filters) {
      return this.http.post<Loan[]>(`${API_URL}/all`, filters, { headers });
    } else {
      return this.http.post<Loan[]>(`${API_URL}/all`, {}, { headers });
    }
  }
}