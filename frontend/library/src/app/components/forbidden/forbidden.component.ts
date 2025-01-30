import { Component } from '@angular/core';
import { Menu } from '../../interfaces/menu';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-forbidden',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './forbidden.component.html',
  styleUrl: './forbidden.component.css'
})
export class ForbiddenComponent {
  menu: Menu[] = [
    {text:"Save Book", routerLink:"/forbidden/save-book"},
    {text:"Book List", routerLink:"/forbidden/book-list"},
    {text:"User List", routerLink:"/forbidden/user-list"},
    {text:"Loan List", routerLink:"/forbidden/loan-list"},
    {text:"Save Loan", routerLink:"/forbidden/save-loan"}
]

}
