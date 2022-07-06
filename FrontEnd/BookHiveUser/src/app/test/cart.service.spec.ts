import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { CartService } from '../services/cart.service';
import { environment } from '../../environments/environment';
import { Book } from '../models/book';
import { CartDetails } from '../models/cartDetails';

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

describe('CartService', () => {
  let service: CartService;
  let httpMock : HttpTestingController
  let url = `${environment.url}/user/cart`;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CartDetails],
    });
    httpMock = TestBed.get(HttpTestingController);
    service = TestBed.get(CartDetails);
  });

  // it('should be created', () => {
  //   expect(service).toBeTruthy();
  // });

  //Check if getAllCartDetailsByCartId() method calls GET Http request
  it('getAllCartDetailsByCartId() should get all books', ()=> {
    service.getAllCartDetailsByCartId(2).subscribe((data : any) => {
      expect(data).toEqual(CartDetails)
    });
    const request = httpMock.expectOne('https://bookhive-api-2.herokuapp.com/user/cart/getAllCartDetailsByCartId/2')
    expect(request.request.method).toEqual('GET')
    request.flush({data : book})
  });


  afterEach( () => {
    httpMock.verify();
 });

});
