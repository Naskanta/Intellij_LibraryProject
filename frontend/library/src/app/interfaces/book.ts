export interface Book {
    title: string,
    author: string,
    publisher: string,
    isbn: string
}


export interface Filters {
    author?: string;
    publisher?: string;
    isbn?: string;
  }