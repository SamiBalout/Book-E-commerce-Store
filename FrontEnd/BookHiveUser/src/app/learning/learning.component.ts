import { Component, OnInit, ViewChild } from '@angular/core';
import { Book } from '../models/book';
import { User } from '../models/user';
import { CartDetails } from '../models/cartDetails';
import {BookService} from '../services/book.service'
import {CartService} from '../services/cart.service'
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';
import { MatCard } from '@angular/material/card';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-learning',
  templateUrl: './learning.component.html',
  styleUrls: ['./learning.component.css']
})
export class LearningComponent implements OnInit {

  panelOpenState = false;
  bookList:Book[]=[]
  cartList:CartDetails[]=[]
  user:User

  pagedList: Book[]= [];
  length: number = 0;

  startIndex : number = 0
  endIndex : number = 10


  @ViewChild(MatPaginator) paginator!: MatPaginator;
  dataSource = new MatTableDataSource<Book>();

  constructor(
    private bookService:BookService,
    private cartService:CartService,
    private snackBar:MatSnackBar
  ) { }

  

  ngOnInit(): void {
    this.bookService.getAllByCategory("Learning").subscribe(data=>{
      this.bookList=data
      //console.log("refresh")
      this.dataSource.data = this.bookList
      this.dataSource.paginator=this.paginator
      this.pagedList = this.bookList.slice(this.startIndex, this.endIndex);

    })
  }


  onPageChange(event: PageEvent){
    let startIndex = event.pageIndex * event.pageSize;
    let endIndex = startIndex + event.pageSize;
    if(endIndex > this.bookList.length){
      endIndex = this.bookList.length;
    }
    this.pagedList = this.bookList.slice(startIndex, endIndex);
    this.startIndex = startIndex
    this.endIndex = endIndex
  }

  //Filtering
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    //console.log(filterValue.length)

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }

    if(filterValue.length > 0) {
      this.dataSource.paginator?.firstPage();
      this.pagedList = this.dataSource.filteredData;
      this.pagedList = this.pagedList.splice(this.startIndex, this.endIndex)
      
    } 

    // else {
    //   this.dataSource.data = this.bookList
    //   console.log(this.bookList)
    //   this.pagedList = this.bookList.splice(this.startIndex, this.endIndex)

    // }
    
    //console.log(this.dataSource.filteredData)
  }




  addToCart(bookId:any){

    let quantity=1;//temp, changed after html page can select quantity
    
    this.user=JSON.parse(localStorage.getItem('user')||'{}');
    if(Object.keys(this.user).length!=0){//if user is login
      this.cartService.getCartIdByCustomerId(this.user.id)
      .subscribe(
        cartId=>{
          this.cartService.addBookToCart(cartId,bookId,quantity)
            .subscribe({
              next:(res)=>{
                //alert('Added to cart');
                this.displayAlert('Added to Cart');
              },
              error:(error)=>{
               // alert('Added to cart failed');
                //console.log(error);
              }
            })
        }
      )
    }else{//if user is not login
      /*
      this.cartList.push(
        {
          book:`${this.bookService.getBookById(bookId)}`,

        })
      localStorage.setItem('cartList',JSON.stringify(this.cartList));*/
      alert('Please login to add book to cart');
    }
  }
  displayAlert(message:any){
    this.snackBar.open(message, "", {
      duration: 2000,panelClass:['mat-toolbar','mat-primary'],
    });
  }

}
