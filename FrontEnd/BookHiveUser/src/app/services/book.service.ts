import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from '../../environments/environment';
import { Book } from '../models/book';

@Injectable({ providedIn: 'root' })
export class BookService {

    url = `${environment.url}/book`;

    constructor(
        private httpClient: HttpClient
    ){}

    getAllBook(){
        return this.httpClient.get<Book[]>(`${this.url}/getAll`)
    }

    getBookById(id:number){
        return this.httpClient.get<Book>(`${this.url}/get/${id}`)
    }

    getAllByCategory(category:String){
        console.log(category);
        return this.httpClient.get<Book[]>(`${this.url}/getAllByCategory/${category}`)
    }

    getAllByName(name:String){
        return this.httpClient.get<Book[]>(`${this.url}/getAllByName/${name}`)
    }

    getAllByGenre(genre:String){
        return this.httpClient.get<Book[]>(`${this.url}/getAllByName/${genre}`)
    }

    getAllBySale(){
        return this.httpClient.get<Book[]>(`${this.url}/getAllOnSale`)
    }
    getAllBySaleAll(){
        return this.httpClient.get<Book[]>(`${this.url}/getAllOnSaleAll`)
    }
}