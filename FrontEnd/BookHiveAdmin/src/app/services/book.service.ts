import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { Book } from '../models/book';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private httpClient : HttpClient) { }

  //List of urls from back end
  addBookUrl = 'https://bookhive-api-2.herokuapp.com/book/add'
  getByIdBookUrl = 'https://bookhive-api-2.herokuapp.com/book/get/'
  getAllUrl = 'https://bookhive-api-2.herokuapp.com/book/getAll'
  updateBookUrl = 'https://bookhive-api-2.herokuapp.com/book/update/'
  deleteBookUrl = 'https://bookhive-api-2.herokuapp.com/book/delete/'

  testUrl = 'http://localhost:3000/books'


  //Add Books
  addBook(book : Book) : Observable<any>{
    return this.httpClient.post<Book[]>(this.addBookUrl, book)
  }

  //Get Book by Id
  getBookById(id : any) {
    return this.httpClient.get<Book[]>(this.getByIdBookUrl + `${id}`)
  }

  //Get All Books
  getAllBooks() {
    return this.httpClient.get<Book[]>(this.getAllUrl)
  }

  //Update Book by Id
  updateBookById(id : any, book : Book) {

  return this.httpClient.put<Book[]>(this.updateBookUrl + `${id}`,book)

  }

  //Delete Book by Id
  deleteBookById(id : any) {
    return this.httpClient.delete(`${this.deleteBookUrl}${id}`)
  }




}
