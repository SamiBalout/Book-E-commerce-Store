import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import {Router, NavigationEnd,ActivatedRoute} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

import { CartComponent } from '../cart/cart.component';
import { CartDetails } from '../models/cartDetails';
import { User } from '../models/user';
import { Address } from '../models/address';
import {CartService} from '../services/cart.service'
import {OrderService} from '../services/orders.service'
import { AccountService } from '../services/account.service';

describe('CartComponent', () => {
  let component: CartComponent;
  let fixture: ComponentFixture<CartComponent>;
  let bookService : CartService

  /*
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
*/

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Router,MatSnackBar,NgModule],
      declarations: [ CartComponent],
      providers : [CartService,OrderService,AccountService]
    })
    .compileComponents();
    
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy(); 
  });

  //Check if ngOnInit method Exists
  it('should check if ngOnInit() exists', () => {
    expect(component.ngOnInit).toBeTruthy();
  });

  //Checks if checkout method Exists
  it('should check if checkout() exists', () => {
    expect(component.checkout).toBeTruthy();
  });

   //Checks if delete method Exists
   it('should check if delete() exists', () => {
    expect(component.delete).toBeTruthy();
  });

   //Checks if update method Exists
   it('should check if update() exists', () => {
    expect(component.update).toBeTruthy();
  });


});
