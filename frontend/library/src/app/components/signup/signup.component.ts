import { Component, inject } from '@angular/core';
import { FormGroup, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import {MatButtonModule} from '@angular/material/button';
import { UserService } from '../../services/user.service';
import { User } from '../../interfaces/user';



@Component({
  selector: 'app-signup',
  standalone:true,
  imports: [ReactiveFormsModule, MatButtonModule, MatFormFieldModule, MatInputModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {

  userService = inject(UserService)
  registrationStatus: {success: boolean, message: string} = {success:false, message: "Not attempted yet"}

  form = new FormGroup({
    firstname: new FormControl('', Validators.required),
    lastname: new FormControl('', Validators.required),
    username: new FormControl('', Validators.required),
    password: new FormControl('', [Validators.required, Validators.pattern('(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[@#$%!^&*]).{8,}')]),
    email: new FormControl('', [Validators.required, Validators.email]),
    role: new FormControl('', Validators.required)
  })

  onSubmit(value:any){
    console.log(value);
    const user: User = {
      firstname: this.form.get('fistname')?.value || '',
      lastname: this.form.get('lastname')?.value || '',
      username: this.form.get('username')?.value || '',
      password: this.form.get('password')?.value || '',
      email: this.form.get('email')?.value || '',
      role: this.form.get('role')?.value || ''
    }
    this.userService.saveUser(user).subscribe({
      next: (response) => {
        console.log("No Error", response)
        this.registrationStatus = {success: true, message: response.msg}
      },
      error: (response) => {
        console.log("Errors", response)
        this.registrationStatus = {success: false, message: response.msg}
      }

    })

    }

    registerAnother(){
      this.form.reset();
      this.registrationStatus = {success: false, message:'Not attempted yet'}
    }
  }

