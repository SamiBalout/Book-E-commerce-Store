import { Book } from "./book"
import { Cart } from "./cart";

export class CartDetails{
    id?:number;
    book?:Book;
    cart?:Cart;
    quantity?:number;
}