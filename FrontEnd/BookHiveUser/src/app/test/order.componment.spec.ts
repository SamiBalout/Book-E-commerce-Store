import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router, ActivatedRoute } from '@angular/router';

import { OrderComponent } from '../order/order.component';
import { User } from '../models/user';
import { OrderService } from '../services/orders.service';
import { MatPaginator } from '@angular/material/paginator';
import { Observable } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { Order } from '../models/order';

describe('BookdashboardComponent', () => {
  let component: OrderComponent;
  let fixture: ComponentFixture<OrderComponent>;
  let orderService : OrderService

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ MatPaginator,Observable,MatTableDataSource],
      declarations: [ OrderComponent],
      providers : [Router,OrderService]
    })
    .compileComponents();
    
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderComponent);
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

});
