import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from '../../environments/environment';
import { Book } from '../models/book';
import { CartDetails } from '../models/cartDetails';

@Injectable({ providedIn: 'root' })
export class CartService {

    url = `${environment.url}/user/cart`;

    constructor(
        private httpClient: HttpClient
    ){}

    addNewCartForUser(userId:any){
        return this.httpClient.post(`${this.url}/addNewCartForUser/${userId}`,{})
    }

    getCartIdByCustomerId(userId:any){
        return this.httpClient.get(`${this.url}/getCartIdByCustomerId/${userId}`)
    }

    getAllCartDetailsByCartId(cartId:any){
        return this.httpClient.get<CartDetails[]>(`${this.url}/getAllCartDetailsByCartId/${cartId}`)
    }

    addBookToCart(cartId:any,bookId:number,quantity:number){
        return this.httpClient.post(`${this.url}/addBookToCart/${bookId}/${cartId}/${quantity}`,{})
    }
    
    updateBookInCart(cartId:any,bookId:number,quantity:number){
        return this.httpClient.put(`${this.url}/updateBookInCart/${cartId}/${bookId}/${quantity}`,{})
    }

    deleteBookFromCart(cartId:any,bookId:number){
        return this.httpClient.delete(`${this.url}/deleteBookFromCart/${cartId}/${bookId}`,{responseType:'text'})
        //without {responseType:'text'}, status is 200 but error catched
    }


}