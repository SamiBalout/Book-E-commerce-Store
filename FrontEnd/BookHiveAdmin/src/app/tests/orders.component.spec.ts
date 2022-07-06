import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { of } from 'rxjs';
import { OrderDetails } from '../models/orderDetails';
import { Orders } from '../models/orders';

import { OrdersComponent } from '../orders/orders.component';
import { OrdersService } from '../services/orders.service';

describe('OrdersComponent', () => {
  let component: OrdersComponent;
  let fixture: ComponentFixture<OrdersComponent>;
  let ordersService : OrdersService

  const orders : Orders = {
    id : 4,
    user : 5,
    address : "123 Oaks Avenue",
    status : "Pending",
    dateTime : '2020-03-04',
    time : '12:04:09',
    orderDetails : OrderDetails

  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrdersComponent ],
      imports: [HttpClientTestingModule, HttpClientModule, MatSnackBarModule, BrowserAnimationsModule],
      providers: [OrdersService, HttpClientModule, MatSnackBarModule],
    })
    .compileComponents();
    ordersService = TestBed.get(OrdersService);
    spyOn(ordersService, 'getAllOrders').and.returnValue(of());
    spyOn(ordersService, 'getOrderDetailsByOrderId').and.returnValue(of(''));
    spyOn(ordersService, 'updateOrderStatusById').and.returnValue(of());
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  //Checks if ngOnInit method exists
  it('Checks if ngOnInit() exists', ()=> {
    expect(component.ngOnInit).toBeTruthy();
  });

  //Check if ngOnInit() calls orders service to get all orders
  it('Checks if ngOnInit() calls orders service to get all orders', ()=> {
    component.ngOnInit();
    expect(ordersService.getAllOrders).toHaveBeenCalled();
  })

  //Check if ngOnInit calls Orders service to get order details by id
  // it('Checks if ngOnInit() calls orders service to get order details', ()=> {
  //   component.ngOnInit();
  //   expect(ordersService.getOrderDetailsByOrderId).toHaveBeenCalled(); 
  // })

  //Check if onDelivered method exists
  it('Checks if onDelivered() exists', ()=> {
    expect(component.onDelivered).toBeTruthy();
  });

  //Check if onDelivered method calls orders service
  // it('Checks if onDelivered() Calls orders service to get order details', ()=> {
  //   component.onDelivered;
  //   expect(ordersService.updateOrderStatusById).toHaveBeenCalled();
  // });

  


});
