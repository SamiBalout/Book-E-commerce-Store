import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';

import { BookComponent } from '../book/book.component';
import { Book } from '../models/book';
import { BookService } from '../services/book.service';

describe('BookdashboardComponent', () => {
  let component: BookComponent;
  let fixture: ComponentFixture<BookComponent>;
  let bookService : BookService

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

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ HttpClientModule, FormBuilder, NgModule],
      declarations: [ BookComponent],
      providers : [BookService]
    })
    .compileComponents();
    
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BookComponent);
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

  //Checks if Add Book method Exists
  it('should check if addToCart() exists', () => {
    expect(component.addToCart).toBeTruthy();
  });

});
