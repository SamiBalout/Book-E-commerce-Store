import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { OrderDetails } from '../models/orderDetails';
import { Orders } from '../models/orders';
import { OrdersService } from '../services/orders.service';

describe('OrdersService', () => {
  let service : OrdersService;
  let httpMock : HttpTestingController

  const orders : Orders = {
    id : 4,
    user : 5,
    address : "123 Oaks Avenue",
    status : "Pending",
    dateTime : '2020-03-04',
    time : '12:04:09',
    orderDetails : OrderDetails
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [OrdersService, HttpClientTestingModule],
      });
      httpMock = TestBed.get(HttpTestingController);
      service = TestBed.get(OrdersService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  //Check if getAllOrders() method calls GET HTTP Request
  it('getAllOrders() should get all orders from API', ()=> {
    service.getAllOrders().subscribe((order : any) => {
      expect(order.order).toEqual(orders)
    });
    const request = httpMock.expectOne('https://bookhive-api-2.herokuapp.com/admin/order/getAll')
    expect(request.request.method).toEqual('GET')
    request.flush({order : orders})
  });

  //Check if getOrderDetailsByOrderId() method calls GET method
  it('getOrderDetailsByOrderId() should get orders details by id', ()=> {
    service.getOrderDetailsByOrderId(orders.id).subscribe((order : any)=> {
      expect(order.order).toEqual(orders)
    });
    const request = httpMock.expectOne('https://bookhive-api-2.herokuapp.com/admin/order/getOrderDetailsByOrderId/4') 
    expect(request.request.method).toEqual('GET')
    request.flush({order : orders})
  });

  //Check if updateOrderStatusById() method calls PUT method
  it('updateOrderStatusById() should update order status', ()=> {
    service.updateOrderStatusById(orders.id, orders.orderDetails).subscribe((order : any)=> {
      expect(order.order).toEqual(orders)
    });
    const request = httpMock.expectOne('https://bookhive-api-2.herokuapp.com/admin/order/updateStatus/4')
    expect(request.request.method).toEqual('PUT')
    request.flush({order : orders})
  });




});
