export interface Loan {
    loanDate: string
  returnDate: string
  book: Book
  user: User
}

export interface Book {
  title: string
}

export interface User {
  username: string
}

