import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { SignupComponent } from './components/signup/signup.component';
import { ForbiddenComponent } from './components/forbidden/forbidden.component';
import { UserLoginComponent } from './components/user-login/user-login.component';
import { authGuard } from './shared/guards/auth.guard';
import { SaveBookComponent } from './components/cruds/save-book/save-book.component';
import { BookListComponent } from './components/cruds/book-list/book-list.component';
import { UserListComponent } from './components/cruds/user-list/user-list.component';
import { LoanListComponent } from './components/cruds/loan-list/loan-list.component';
import { SaveLoanComponent } from './components/cruds/save-loan/save-loan.component';

export const routes: Routes = [
    {path: 'home', component:HomeComponent},
    {path: '', redirectTo:'/home', pathMatch: 'full'},
    {path:'signup', component:SignupComponent},
    {path:'forbidden', component:ForbiddenComponent, canActivate:[authGuard]},
    {path:'user-login', component:UserLoginComponent},
    {path:'forbidden/save-book', component:SaveBookComponent, canActivate:[authGuard]},
    {path:'forbidden/book-list', component:BookListComponent, canActivate:[authGuard]},
    {path:'forbidden/user-list', component:UserListComponent, canActivate:[authGuard]},
    {path:'forbidden/loan-list', component:LoanListComponent, canActivate:[authGuard]},
    {path:'forbidden/save-loan', component:SaveLoanComponent, canActivate:[authGuard]}
];
