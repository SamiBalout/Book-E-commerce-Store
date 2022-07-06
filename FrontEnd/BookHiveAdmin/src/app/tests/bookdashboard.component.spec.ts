import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { BookdashboardComponent } from '../bookdashboard/bookdashboard.component';
import { Book } from '../models/book';
import { BookService } from '../services/book.service';
import { RouterTestingModule } from '@angular/router/testing';
import { AppRoutingModule } from '../app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { BrowserModule } from '@angular/platform-browser';

import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatCardModule} from '@angular/material/card';
import {MatDividerModule} from '@angular/material/divider';
import {MatFormFieldModule} from '@angular/material/form-field';
import { FlexLayoutModule } from '@angular/flex-layout';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatTableModule} from '@angular/material/table';

//import { DataSource } from '@angular/cdk/collections';
import {MatPaginatorModule} from '@angular/material/paginator';

import {MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule, MatNavList} from '@angular/material/list';
import {MatSelectModule} from '@angular/material/select';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatSnackBarModule} from '@angular/material/snack-bar';

//Firebase 
import {AngularFireModule} from '@angular/fire/compat';
import { environment } from 'src/environments/environment.prod';
import {AngularFireStorageModule} from "@angular/fire/compat/storage";
import {MatMenuModule} from '@angular/material/menu';
import {MatBadgeModule} from '@angular/material/badge';
import { RouterService } from '../services/router.service';
import { AppComponent } from '../app.component';
import { FooterComponent } from '../footer/footer.component';
import { HeaderComponent } from '../header/header.component';
import { Observable, of } from 'rxjs';


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
  imageUrl : "",
  active : true,
  onSale : true
}

describe('BookdashboardComponent', () => {
  let component: BookdashboardComponent;
  let fixture: ComponentFixture<BookdashboardComponent>;
  let bookService : BookService

 

  beforeEach(async (() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule, RouterTestingModule, BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        // ReactiveFormsModule,
        // FormsModule,
        // MatToolbarModule,
        // MatIconModule,
        // MatCardModule,
        // MatDividerModule,
        // MatFormFieldModule,
        // FlexLayoutModule,
        // MatInputModule,
        // MatButtonModule,
        // HttpClientModule,
        // MatTableModule,
        // MatPaginatorModule,
        MatDialogModule,
        // MatSidenavModule,
        // MatListModule,
        // MatSelectModule,
        // MatExpansionModule,
        // AngularFireStorageModule,
        AngularFireModule.initializeApp(environment.firebaseConfig, "cloud"),
        // MatMenuModule,
        // MatBadgeModule,
        MatSnackBarModule
      ],
      declarations: [ BookdashboardComponent],
      providers : [BookService, RouterService, FormBuilder]

  })
    .compileComponents();
    bookService = TestBed.get(BookService);
    spyOn(bookService, 'addBook').and.returnValue(of(''));
    spyOn(bookService, 'getBookById').and.returnValue(of());
    spyOn(bookService, 'getAllBooks').and.returnValue(of());
    spyOn(bookService, 'updateBookById').and.returnValue(of());
    spyOn(bookService, 'deleteBookById').and.returnValue(of(''));

  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookdashboardComponent);
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
  it('should check if addBook() exists', () => {
    expect(component.addBook).toBeTruthy();
  });

  //Check if Remove Book method exists
  it('should check if removeBook() exists', () => {
    expect(component.removeBook).toBeTruthy();
  });

  //Check if open dialog method exists for update book
  it('should check if openDialog() exists', () => {
    expect(component.openDialog).toBeTruthy();
  });

  //Checks if book service is called to get all book
  it('Checks if ngOnIt() calls book service to get all books', ()=> {
    component.ngOnInit();
    expect(bookService.getAllBooks).toHaveBeenCalled();
  });

  //Checks if add book method calls book service
  it('Checks if addBook() calls book service to add book', ()=> {

    // const id = component.bookForm.controls.id
    // id.setValue(book.id)
    // const name = component.bookForm.controls.name
    // name.setValue(book.name)
    // const price = component.bookForm.controls.price
    // price.setValue(book.price)
    // const type = component.bookForm.controls.type
    // type.setValue(book.type)

    // const genre = component.bookForm.controls.genre
    // genre.setValue(book.genre)
    // const category = component.bookForm.controls.category
    // category.setValue(book.category)
    // const author = component.bookForm.controls.author
    // author.setValue(book.author)

    // const description = component.bookForm.controls.description
    // description.setValue(book.description)
    // const format = component.bookForm.controls.format
    // format.setValue(book.format)
    // const rating = component.bookForm.controls.rating
    // rating.setValue(book.rating)

    // const quantity = component.bookForm.controls.quantity
    // quantity.setValue(book.quantity)
    // const imageUrl = component.bookForm.controls.imageUrl
    // imageUrl.setValue(book.imageUrl)
    // const active = component.bookForm.controls.active
    // active.setValue(book.active)
    // const onSale = component.bookForm.controls.onSale
    // onSale.setValue(book.onSale)

    //component.addBook(book);
    //expect(bookService.addBook).toHaveBeenCalled();
  });

  //Checks if remove book method calls book service
  it('Checks if removeBook() calls book service to remove book', ()=> {
    component.removeBook(book);
    expect(bookService.deleteBookById).toHaveBeenCalled();
  });

  

});
