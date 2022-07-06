import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { User } from '../models/user';
import { OrderService } from '../services/orders.service';
import { MatPaginator } from '@angular/material/paginator';
import { Observable } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { Order } from '../models/order';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  orderList: Order[] = [];

  user: User

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  obs!: Observable<any>;
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();

  constructor(
    private orderService: OrderService,
    private router: Router 
  ) { }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('user') || '{}');
    if(Object.keys(this.user).length!=0){//if user is login
      this.orderService.getOrderByCustomerId(this.user.id).subscribe(data => {
        this.orderList = data;
        this.orderList.forEach((order: any, index: number) => {
          this.orderService.getOrderDetailsByOrderId(order.id).subscribe(data => {
            this.orderList[index].orderDetails = data;
            this.orderList[index].total = this.sumOrderCost(data);
          });
        });
  
        this.dataSource.paginator = this.paginator;
        this.dataSource.data = this.orderList;
        this.obs = this.dataSource.connect();
      });
    }else{
      alert('Please login to view order');
      this.router.navigate(['../login']);
    }
    
  }

  sumOrderCost(orderDetails: any) {
    let sum = 0;
    orderDetails.forEach((orderDetail: any) => {
      sum += orderDetail.quantity * orderDetail.book.price;
    })

    return (Math.round(sum * 100) / 100).toFixed(2);;
  }

}
