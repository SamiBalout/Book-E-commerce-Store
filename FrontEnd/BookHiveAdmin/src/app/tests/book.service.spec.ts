import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { Book } from '../models/book';

import { BookService } from '../services/book.service';

const book : Book = {
  id : 3,
  name :	"Harry Potter",
  price : 34.99,
  type : "Novel",
  genre : "Mystery",
  category :	"Mystery",
  author : "J.K. Rowling",
  description :	"Harry and friends discover something unusual at Hogwarts",
  format : "Novel",
  rating : 3,
  quantity : 40,
  imageUrl : "https://images-na.ssl-images-amazon.com/images/I/710ESoXqVPL.jpg",
  active : true,
  onSale : true
}

describe('BookService', () => {
  let service: BookService;
  let httpMock : HttpTestingController

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [BookService],
    });
    httpMock = TestBed.get(HttpTestingController);
    service = TestBed.get(BookService);
  });

  // it('should be created', () => {
  //   expect(service).toBeTruthy();
  // });

  //Check if addBook() method calls POST HTTP request 
  it('addBook() should add books', ()=> {
    service.addBook(book).subscribe(bookData=> {
      expect(bookData.data).toEqual(book)
    });
    const request = httpMock.expectOne('https://bookhive-api-2.herokuapp.com/book/add')
    expect(request.request.method).toEqual('POST')
    request.flush({data : book})
  });

  //Check if getBookById() method calls GET Http request
  it('getBookById() should get book by Id', ()=> {
    service.getBookById(book.id).subscribe((bookData : any)=> {
      expect(bookData.data).toEqual(book)
    });
    const request = httpMock.expectOne('https://bookhive-api-2.herokuapp.com/book/get/3')
    expect(request.request.method).toEqual('GET')
    request.flush({data : book})
  });

  //Check if getAllBooks() method calls GET Http request
  it('getAllBooks() should get all books', ()=> {
    service.getAllBooks().subscribe((bookData : any) => {
      expect(bookData.data).toEqual(book)
    });
    const request = httpMock.expectOne('https://bookhive-api-2.herokuapp.com/book/getAll')
    expect(request.request.method).toEqual('GET')
    request.flush({data : book})
  });

  //Check if updateBookBookById() method calls PUT request
  it('updateBookById() should get all books', ()=> {
    service.updateBookById(book.id, book).subscribe();
    const request = httpMock.expectOne('https://bookhive-api-2.herokuapp.com/book/update/3')
    expect(request.request.method).toEqual('PUT')
    request.flush({data : book})
  });

  //Check if deleteBookBookById() method calls DELETE request
  it('deleteBookById() should delete book by id', ()=> {
    service.deleteBookById(book.id).subscribe((bookData : any)=> {
      expect(bookData.data).toEqual(book)
    });
    const request = httpMock.expectOne('https://bookhive-api-2.herokuapp.com/book/delete/3')
    expect(request.request.method).toEqual('DELETE')
    request.flush({data : book})
  });

  afterEach( () => {
    httpMock.verify();
 });

});
