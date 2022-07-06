import { Component, OnInit } from '@angular/core';
import {Router, NavigationEnd,ActivatedRoute} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';

import { CartDetails } from '../models/cartDetails';
import { User } from '../models/user';
import { Address } from '../models/address';
import {CartService} from '../services/cart.service'
import {OrderService} from '../services/orders.service'
import { AccountService } from '../services/account.service';



@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  cartList:CartDetails[]=[]
  user:User
  address:Address={}
  cartId:any
  quantityList=[1,2,3,4,5,6,7,8,9]
  subtotal:string

  constructor(
    private router: Router,
    private cartService:CartService,
    private orderService:OrderService,
    private userService: AccountService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.user=JSON.parse(localStorage.getItem('user')||'{}');
    if(Object.keys(this.user).length!=0){//if user is login
    this.userService.GetContactDetails(this.user.id).subscribe((data:any)=>{
      this.user.address = data.address;
      console.log(this.user);
      });
      this.cartService.getCartIdByCustomerId(this.user.id).subscribe(data=>{
        this.cartId=data;
        this.cartService.getAllCartDetailsByCartId(this.cartId).subscribe(data=>{   
          this.cartList=data;
          this.subtotal=this.sumCartSubtotal(this.cartList);
        })
      })
    }else{//if user is not login
      this.cartList=JSON.parse(localStorage.getItem('cartList')||'{}');
    }     
  }

  delete(bookId:any){
    if(Object.keys(this.user).length!=0){//if user is login
      this.cartService.deleteBookFromCart(this.cartId,bookId)
      .subscribe({
        next:(res)=>{
          //alert('Delete successful!');
          this.displayAlert("Delete successful!");
          window.location.reload();
          console.log(res);
        },
        error:(error)=>{
          //this.refreshComponent();
          //alert('Delete failed!');
          this.displayAlert("Delete failed!");
          console.log(error);
        }
      })
    }else{
      localStorage.removeItem('cartList');
      alert('Delete successful!');
      window.location.reload();
    }
    
  }
  update(bookId:any, bookQuantity:any){
    this.cartService.updateBookInCart(this.cartId,bookId,bookQuantity).subscribe({
      next: (res) =>{
        this.displayAlert('Update successful!');
        window.location.reload();
      },
      error: (err)=>{

      }
    });

  }
  
  checkout(){
    if(Object.keys(this.user).length!=0){//if user is login
      this.cartService.getCartIdByCustomerId(this.user.id)
      .subscribe(
        data=>{
          this.cartService.getAllCartDetailsByCartId(this.cartId).subscribe(data2=>{ 
            if(Object.keys(data2).length!=0){//check is cart empty
            this.address.address=this.user.address;
            this.orderService.addNewOrder(data,this.address)
              .subscribe({
                next:(res)=>{
                  this.displayAlert('Order placed successful!');
                  window.location.reload();
                  console.log(res);
                },
                error:(error)=>{
                  //this.refreshComponent();
                  this.displayAlert(error.error.message);
                  //console.log(error.error.message);
                }
              })
            }else{
              //alert('Cart is empty!');
              this.displayAlert('Cart is empty!');
            }
          })
        }
      )
    }else{
      this.displayAlert('Please login to checkout!');
      //alert('Please login to checkout!')
    }
  }

  addNewCartForUser(){
    this.cartService.addNewCartForUser(this.user.id);
  }

  sumCartSubtotal(cartList: any) {
    let sum = 0;
    cartList.forEach((cartList: any) => {
      sum += cartList.quantity * cartList.book.price;
    })

    return (Math.round(sum * 100) / 100).toFixed(2);;
  }

  refreshComponent(){//find online eazy way to refresh page after change
    // window.location.reload();
    this.cartService.getAllCartDetailsByCartId(this.cartId).subscribe((data: any) => {
      this.cartList = data;
      this.subtotal = this.sumCartSubtotal(this.cartList);
    });
  }
  displayAlert(message:any){
    this.snackBar.open(message, "", {
      duration: 5000,panelClass:['mat-toolbar','mat-warn'],
    });
  }
}

