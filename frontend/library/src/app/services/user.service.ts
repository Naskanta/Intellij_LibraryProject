import { HttpClient } from '@angular/common/http';
import { Injectable, inject, signal, effect} from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Router } from '@angular/router';
import { Credentials, User } from '../interfaces/user';
import { LoggedInUser } from '../interfaces/user';
import { jwtDecode } from 'jwt-decode';
import { HttpHeaders } from '@angular/common/http';

const API_URL=`${environment.apiURL}/users`
const AUTH_API_URL=`${environment.apiURL}/auth`




@Injectable({
  providedIn: 'root'
})
export class UserService {
    http: HttpClient = inject(HttpClient)
    router = inject(Router)
    user = signal<LoggedInUser | null>(null)

    constructor(){
      const token = localStorage.getItem("token")
      if(token){
        const decodedTokenSubject = jwtDecode(token).sub as unknown as LoggedInUser

        this.user.set({
          lastname: decodedTokenSubject.lastname,
          email: decodedTokenSubject.email,
          role: decodedTokenSubject.role
        })
      }
      effect(() =>{
        if(this.user()){
          console.log("User loggen in: ", this.user()?.lastname);
        } else {
          console.log('No user logged in');
        }
      })
    }

    saveUser(user: User) {
      return this.http.post<{msg: string}>(`${API_URL}/save`, user)
    }


  loginUser(credentials: Credentials){
    return this.http.post<{token: string}>(`${AUTH_API_URL}/login`, credentials)
  }

  logOutUser(){
    this.user.set(null);
    localStorage.removeItem('token');
    this.router.navigate(['user-login']);
  }

  getUserRole(): string | null {
    return this.user()?.role || null;
  }

  getAllUsers(filters?: User) {
    const token = localStorage.getItem('token');
    let headers = new HttpHeaders();
    
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }

    if (filters) {
      return this.http.post<User[]>(`${API_URL}/all`, filters, { headers });
    } else {
      return this.http.post<User[]>(`${API_URL}/all`, {}, { headers });
    }
  }
}
