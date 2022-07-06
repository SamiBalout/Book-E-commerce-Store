import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

import { Address } from '../models/address';

@Injectable({ providedIn: 'root' })
export class OrderService {
  url = `${environment.url}/user/order`;

  constructor(
    private httpClient: HttpClient
  ) { }

  getOrderByCustomerId(userId: any): Observable<any> {
    return this.httpClient.get<any>(`${this.url}/getByCustomerId/${userId}`);
  }

  getOrderDetailsByOrderId(orderId: any): Observable<any> {
    return this.httpClient.get<any>(`${this.url}/getOrderDetailsByOrderId/${orderId}`);
  }

  addNewOrder(cardId: any, address: Address) {
    return this.httpClient.post(`${this.url}/addNewOrder/${cardId}`, address);
  }
}
