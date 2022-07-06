import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { time } from 'console';
import { Book } from '../models/book';
import { Orders } from '../models/orders';
import { OrdersService } from '../services/orders.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  ordersList : Orders [] = []
  index : any;
  isDelivered = false

  temp : any;



  panelOpenState = false;

  //Columns Names
  displayedColumns: string[] = ['id', 'customerId', 'orderDetails', 'address', 'status', 
  'dateTime', 'time'];
  dataSource = new MatTableDataSource<Book>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  //Inject Orders Service
  constructor(private ordersService : OrdersService, private snackBar:MatSnackBar) { }

  ngOnInit(): void {

    //Get All Orders
    this.ordersService.getAllOrders().subscribe((data : any)=> {

      this.ordersList = data
      this.dataSource.data=this.ordersList
      console.log(this.dataSource)
      this.dataSource.paginator = this.paginator;
      console.log(this.ordersList)
      


      //Get Order Details By Order Id
      //This is the index
      var count = 0;
      this.ordersList.forEach(data=> { 
        this.index=data.id
        console.log(data.id)


        //Splitting date and time
        var time;
        var date;
        this.temp = data.dateTime
        date = this.temp.split('T')[0]
        time = this.temp.split('T')[1]
        data.dateTime = date
        data.time = time.split('.')[0]

        this.ordersService.getOrderDetailsByOrderId(data.id).subscribe({
          next:(res) => {
            console.log(this.ordersList[0])
            //Index does not go off of size, it goes off of Order Id
            this.ordersList[count].orderDetails = res
            console.log(res)
            count++
            
          }, 
          error:(error) => {
            count++
          }

        })
        
      })

    })

  
  }

   //Filtering Order
   applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }


  onDelivered(order : Orders, status : string) {

    if(status.toLowerCase().localeCompare("pending") == 0) {
      status = 'Delivered'
    } 

    else {
      status = 'Pending'
    }
    this.displayAlert("Status Updated!");

    this.ordersService.updateOrderStatusById(order.id, status).subscribe(data=> {
      //data.status = 'Delivered'
      console.log(data.status)
      order.status = data.status 
      
    })

  }
  displayAlert(message:any){
    this.snackBar.open(message, "", {
      duration: 2000,panelClass:['mat-toolbar','mat-primary'],
    });
  }

}
