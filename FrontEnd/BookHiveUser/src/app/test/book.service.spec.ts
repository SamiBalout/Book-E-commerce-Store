import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { BookService } from '../services/book.service';
import { environment } from '../../environments/environment';
import { Book } from '../models/book';


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
  let url = `${environment.url}/book`;

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

  //Check if getAllBooks() method calls GET Http request
  it('getAllBook() should get all books', ()=> {
    service.getAllBook().subscribe((bookData : any) => {
      expect(bookData.data).toEqual(book)
    });
    const request = httpMock.expectOne('https://bookhive-api-2.herokuapp.com/book/getAll')
    expect(request.request.method).toEqual('GET')
    request.flush({data : book})
  });

  //Check if getBookById() method calls GET Http request
  it('getBookById() should get book by Id', ()=> {
    service.getBookById(3).subscribe((bookData : any)=> {
      expect(bookData.data).toEqual(book)
    });
    const request = httpMock.expectOne('https://bookhive-api-2.herokuapp.com/book/get/3')
    expect(request.request.method).toEqual('GET')
    request.flush({data : book})
  });

  //Check if getAllBySale method calls GET Http request
  it('getAllBySale should get all books', ()=> {
    service.getAllBySale().subscribe((bookData : any) => {
      expect(bookData.data).toEqual(book)
    });
    const request = httpMock.expectOne('https://bookhive-api-2.herokuapp.com/book/getAllOnSale')
    expect(request.request.method).toEqual('GET')
    request.flush({data : book})
  });

  //Check if getAllBySale method calls GET Http request
  it('getAllBySale should get all books', ()=> {
    service.getAllBySale().subscribe((bookData : any) => {
      expect(bookData.data).toEqual(book)
    });
    const request = httpMock.expectOne('https://bookhive-api-2.herokuapp.com/book/getAllOnSale')
    expect(request.request.method).toEqual('GET')
    request.flush({data : book})
  });


  afterEach( () => {
    httpMock.verify();
 });

});
