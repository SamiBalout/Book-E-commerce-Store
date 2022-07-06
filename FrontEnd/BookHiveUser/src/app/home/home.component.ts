import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { User } from '../models/user';
import { BookService } from '../services/book.service';
import { CartService } from '../services/cart.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {


  constructor(private bookService:BookService, private cartService:CartService, private snackBar:MatSnackBar) { }
  panelOpenState = false;
  bookSaleList:any;
  user:User;

  ngOnInit(): void {
    // for (let i = 0; i < 5; i++) {
    //   var item = new saleList;
    //   item.id = i;
    //   item.name = "Easter" + i; 
    //   item.price = 10.99 * i;
    //   item.url="https://firebasestorage.googleapis.com/v0/b/book-hive-cgi.appspot.com/o/EasterAd.png?alt=media&token=0c844c63-c472-4697-a531-6b641f6e2448"
    //   this.bookSaleList?.push(item);
    //   console.log(item)
    // }
    this.bookService.getAllBySale().subscribe({
      next:(res)=>{
          this.bookSaleList = res;
      },
      error: (error)=>{

      }
    })
    console.log(this.bookSaleList)
    
  }
  addToCart(bookId:any){
    //alert("Book id: " + bookId);
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
                this.displayAlert('Added to cart');
              },
              error:(error)=>{
                //alert('Added to cart failed');
                console.log(error);
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

export class saleList{
  id?:number;
  name?:String;
  price?:number;
  url?:String;
}
