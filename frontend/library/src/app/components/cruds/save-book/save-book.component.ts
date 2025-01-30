import { Component, inject } from '@angular/core';
import { FormGroup, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { BookService } from '../../../services/book.service';
import { Book } from '../../../interfaces/book';
import { RouterLink } from '@angular/router';


@Component({
  selector: 'app-save-book',
  standalone:true,
  imports: [RouterLink, ReactiveFormsModule, MatButtonModule, MatFormFieldModule, MatInputModule],
  templateUrl: './save-book.component.html',
  styleUrl: './save-book.component.css'
})
export class SaveBookComponent {

    bookService = inject(BookService)
    saveStatus: { success: boolean; message: string } = { success: false, message: 'Not attempted yet' };

    form = new FormGroup({
      title: new FormControl('', Validators.required),
      author: new FormControl('', Validators.required),
      publisher: new FormControl('', Validators.required),
      isbn: new FormControl('', [Validators.required, Validators.pattern('[0-9]{10,13}')])
    });


    onSubmit(value: any) {
      const book: Book = {
        title: this.form.get('title')?.value || '',
        author: this.form.get('author')?.value || '',
        publisher: this.form.get('publisher')?.value || '',
        isbn: this.form.get('isbn')?.value || ''
      };

      this.bookService.saveBook(book).subscribe({
        next: (response) => {
          console.log('Book saved successfully', response);
          this.saveStatus = { success: true, message: 'Book saved successfully!' };
        },
        error: (error) => {
          console.log('Error saving book', error);
          this.saveStatus = { success: false, message: 'Failed to save book!' };
        }
      });
    }

    saveAnother() {
      this.form.reset();
      this.saveStatus = { success: false, message: 'Not attempted yet' };
    }
   }

  

