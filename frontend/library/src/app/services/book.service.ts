import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment.development';
import { Book } from '../interfaces/book';
import { UserService } from './user.service';
import { Filters } from '../interfaces/book';

const API_URL=`${environment.apiURL}/books`


@Injectable({
  providedIn: 'root'
})
export class BookService {
  http: HttpClient = inject(HttpClient)
  userService = inject(UserService)

  constructor() { }

  saveBook(book: Book){
    const token = localStorage.getItem('token')
    let headers = new HttpHeaders();

   
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }
    return this.http.post<{msg: string}>(`${API_URL}/save`, book, {headers})
  }


  getAllBooks(filters?: Filters) {
    const token = localStorage.getItem('token');
    let headers = new HttpHeaders();
  
    
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }
  
    
    if (filters) {
      return this.http.post<Book[]>(`${API_URL}/all`, filters, { headers });
    } else {
      
      return this.http.post<Book[]>(`${API_URL}/all`, {}, { headers });
    }
  }
  
}
