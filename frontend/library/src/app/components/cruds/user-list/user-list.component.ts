import { Component, inject,  } from '@angular/core';
import { UserService } from '../../../services/user.service';
import { User } from '../../../interfaces/user';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule, MatButtonModule, MatFormFieldModule, MatInputModule, CommonModule],
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent  {
  userService = inject(UserService);
  users: User[] = [];

  constructor() {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.getAllUsers().subscribe({
      next: (response) => {
        this.users = response;
        console.log('Users loaded:', this.users);
      },
      error: (error) => {
        console.log('Error loading users:', error);
      }
    });
  }
}
