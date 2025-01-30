import { Component, inject, OnInit } from '@angular/core';
import { BookService } from '../../../services/book.service';
import { Book } from '../../../interfaces/book';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule, MatButtonModule, MatFormFieldModule, MatInputModule, CommonModule],
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent  {
  bookService = inject(BookService);
  books: Book[] = [];

  constructor() {}

  ngOnInit(): void {
    
    this.loadBooks();
  }

  loadBooks() {
    
    this.bookService.getAllBooks().subscribe({
      next: (response) => {
        this.books = response;
        console.log('Books loaded:', this.books);
      },
      error: (error) => {
        console.log('Error loading books:', error);
      }
    });
  }
}
