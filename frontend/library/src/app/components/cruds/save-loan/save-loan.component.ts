import { Component, inject } from '@angular/core';
import { FormGroup, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { LoanService } from '../../../services/loan.service';
import { BookService } from '../../../services/book.service';
import { UserService } from '../../../services/user.service';
import { Book } from '../../../interfaces/book';
import { User } from '../../../interfaces/user';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-save-loan',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule, MatButtonModule, MatFormFieldModule, MatInputModule],
  templateUrl: './save-loan.component.html',
  styleUrl: './save-loan.component.css'
})
export class SaveLoanComponent {

  loanService = inject(LoanService);
  bookService = inject(BookService);
  userService = inject(UserService);
  
  books: Book[] = [];
  users: User[] = [];

  saveStatus: { success: boolean; message: string } = { success: false, message: 'Not attempted yet' };

  form = new FormGroup({
    loanDate: new FormControl('', Validators.required),
    returnDate: new FormControl('', Validators.required),
    bookTitle: new FormControl('', Validators.required),
    username: new FormControl('', Validators.required)
  });

  constructor() {
    this.loadBooks();
    this.loadUsers();
  }

  formatDateForLocalDateTime(date: string): string {
    if (!date) return '';
    return new Date(date).toISOString().slice(0, 19);  
  }
  loadBooks() {
    this.bookService.getAllBooks().subscribe({
      next: (response) => {
        this.books = response;
      },
      error: (error) => {
        console.log('Error loading books:', error);
      }
    });
  }

  loadUsers() {
    this.userService.getAllUsers().subscribe({
      next: (response) => {
        this.users = response;
      },
      error: (error) => {
        console.log('Error loading users:', error);
      }
    });
  }

  onSubmit(value: any) {
    const loan = {
      loanDate: this.formatDateForLocalDateTime( this.form.get('loanDate')?.value as string) || '',
      returnDate: this.formatDateForLocalDateTime(this.form.get('returnDate')?.value as string) || '',
      book: { title: this.form.get('bookTitle')?.value || '' },
      user: { username: this.form.get('username')?.value || '' }
    };

    this.loanService.saveLoan(loan).subscribe({
      next: (response) => {
        console.log('Loan saved successfully', response);
        this.saveStatus = { success: true, message: 'Loan saved successfully!' };
      },
      error: (error) => {
        console.log('Error saving loan', error);
        this.saveStatus = { success: false, message: 'Failed to save loan!' };
      }
    });
  }

  saveAnother() {
    this.form.reset();
    this.saveStatus = { success: false, message: 'Not attempted yet' };
  }
}
