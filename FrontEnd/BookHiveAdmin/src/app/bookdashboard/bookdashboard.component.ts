import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Book } from '../models/book';
import { BookService } from '../services/book.service';
import { UpdateBookComponent } from '../update-book/update-book.component';
//Firebase 
import { map, finalize } from "rxjs/operators";
import { AngularFireStorage } from '@angular/fire/compat/storage';
import { Observable } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-bookdashboard',
  templateUrl: './bookdashboard.component.html',
  styleUrls: ['./bookdashboard.component.css']
})


// export class TableColumnStylingExample {
//   displayedColumns: string[] = ['demo-position', 'demo-name', 'demo-weight', 'demo-symbol'];
//   dataSource = ELEMENT_DATA;
// }



export class BookdashboardComponent implements OnInit {

  showFiller = false;
  sidenav=true

  //Inject Service
  constructor(private bookService : BookService, private formBuilder : FormBuilder, private matDialog : MatDialog, private storage: AngularFireStorage, private snackBar:MatSnackBar) { }

  //Call Book list
  bookList : Book [] = []
  //Bind the form group
  bookForm : any;

  panelOpenState = false;

  downloadURL!: Observable<string>;

  message='';

  imageUrl: any;

  choices: boolean[] =[true, false]; 

  displayedColumns: string[] = ['id', 'name', 'price', 'category', 
  'author', 'description', 'quantity', 'imageUrl', 'active', 'update', 'remove'];
  dataSource = new MatTableDataSource<Book>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  //Form Builder and Validator
  ngOnInit(): void {
    this.bookForm = this.formBuilder.group({
     // id : ['', [Validators.required]],
      name : ['', [Validators.required]],
      price : ['', [Validators.required]],
      type : ['', [Validators.required]],
      genre : ['', [Validators.required]],
      category : ['', [Validators.required]],
      author : ['', [Validators.required]],
      description : ['', [Validators.required]],
      format : ['', [Validators.required]],
      rating : ['', [Validators.required]],
      quantity : ['', [Validators.required]],
      imageUrl : ['', [Validators.required]],
      active : ['', [Validators.required]],
      onSale: ['',[Validators.required]]  
    })

    //Get All Books
    this.bookService.getAllBooks().subscribe(data => {
      this.bookList = data
      this.dataSource.data=this.bookList
      console.log(this.dataSource)
      this.dataSource.paginator = this.paginator;
      
    })

    //this.dataSource.paginator = tehis.paginator;

  }

  //Filtering
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  //Add Book
  addBook(fileInput: any) {
    if(this.bookForm.value.quantity < 0 || this.bookForm.value.rating < 0 || this.bookForm.value.rating > 5 || this.bookForm.value.price < 0 || fileInput.files.length <= 0 || this.bookForm.value.onSale == ""){
      alert("Error with Data Input(s) (Quantity, Rating, Price, Didnt Select OnSale or File is Missing)")
    }else{
      this.onFileSelected(fileInput);
    }
    

    // if(!this.bookForm.valid) {
    //   this.message="Please Enter Correct Information Required"
    // }
  }


  //Remove Book
  removeBook(book : Book) {
    // let index = this.bookList.findIndex(data => data.id === book.id)
    // if(index !== -1) {
    //   this.bookList.splice(index,1)
      this.bookService.deleteBookById(book.id).subscribe(data=> {

      });

      //window.location.reload();
      this.ngOnInit();
   
  }

  //Update Book with Open Dialog
  openDialog(book : Book) {
    //alert('Update Dialog Open')
    //alert('Updating Book with id : ' + book.id)
    let update = this.matDialog.open(UpdateBookComponent, {
      height:"400px",
      width: "500px",
      data:{id:book.id}
      
    });
    update.afterClosed().subscribe(data=> {
      //location.reload()
      this.ngOnInit();
    })
  }

  onFileSelected(imageInput: any) {
    var n = Date.now();
    const file: File = imageInput.files[0];
    const filePath = `BookImages/${n}` ;
    const fileRef = this.storage.ref(filePath);
    const task = this.storage.upload(`BookImages/${n}`, file);
    task
      .snapshotChanges()
      .pipe(
        finalize(() => {
          this.downloadURL = fileRef.getDownloadURL();
          this.downloadURL.subscribe(url => {
            if (url) {
              this.imageUrl = url;
              this.bookForm.value.imageUrl = this.imageUrl;
              this.bookService.addBook(this.bookForm.value).subscribe({
                next: (res) => {
                  this.message="Data Added Successfully"
                  this.displayAlert('Data Added Successfully');
                },
                error: (error) =>{
                  console.log(error);
                  this.message= error.error;
          
                }
              })
            }
            console.log(this.imageUrl);
          });
        })
      )
      .subscribe((url: any) => {
        if (url) {
          console.log(url);
        }
      });
  }
  displayAlert(message:any){
    this.snackBar.open(message, "", {
      duration: 2000,panelClass:['mat-toolbar','mat-primary'],
    });
  }


}
