import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Orders } from '../models/orders';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

  getAllOrdersUrl = 'https://bookhive-api-2.herokuapp.com/admin/order/getAll'
  getAllOrderByCustomerIdUrl = 'https://bookhive-api-2.herokuapp.com/admin/order/getByCustomerId/'
  updateOrderStatusByIdUrl = 'https://bookhive-api-2.herokuapp.com/admin/order/updateStatus/'
  getOrderDetailsByOrderIdUrl = 'https://bookhive-api-2.herokuapp.com/admin/order/getOrderDetailsByOrderId/'


  //testUrl = 'http://localhost:3000/orders' 

  constructor(private httpClient : HttpClient) { }

  //Get All Orders
  getAllOrders(){
    return this.httpClient.get<Orders[]>(this.getAllOrdersUrl)
  }

  //Get All Orders By Customer Id
  getOrderDetailsByOrderId(id : any) {
    return this.httpClient.get(this.getOrderDetailsByOrderIdUrl + `${id}`)
    
  }

  //Update Order Status By Id
  updateOrderStatusById(id : any, status : string) {
    return this.httpClient.put<Orders>(this.updateOrderStatusByIdUrl + id, {status})

  }


}
