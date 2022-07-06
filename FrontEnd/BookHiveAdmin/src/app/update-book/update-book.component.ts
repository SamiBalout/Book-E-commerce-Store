import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { BookdashboardComponent } from '../bookdashboard/bookdashboard.component';
import { Book } from '../models/book';
import { BookService } from '../services/book.service';

//Firebase 
import { map, finalize } from "rxjs/operators";
import { AngularFireStorage } from '@angular/fire/compat/storage';
import { Observable } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-update-book',
  templateUrl: './update-book.component.html',
  styleUrls: ['./update-book.component.css']
})
export class UpdateBookComponent implements OnInit {

  bookList : any [] = []

  bookForm : any;

  currentUrl: any; 

  downloadURL!: Observable<string>;
  imageUrl : any;
  choices: boolean[] =[true, false]; 
  constructor(@Inject(MAT_DIALOG_DATA) public data: Book, private bookService : BookService, private formBuilder : FormBuilder,
  private matDialogRef : MatDialogRef<BookdashboardComponent>, private storage: AngularFireStorage, private snackBar:MatSnackBar) { }

  ngOnInit(): void {
    //Form Builder and Validator
    this.bookForm = this.formBuilder.group({
      id : [''],
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

    

    //Get book By Id
    
    this.bookService.getBookById(this.data.id).subscribe((data : any)=> {
      this.bookForm.controls['id'].setValue(data.id);
      this.bookForm.controls['name'].setValue(data.name);
      this.bookForm.controls['price'].setValue(data.price);
      this.bookForm.controls['type'].setValue(data.type);
      this.bookForm.controls['genre'].setValue(data.genre);
      this.bookForm.controls['category'].setValue(data.category);
      this.bookForm.controls['author'].setValue(data.author);
      this.bookForm.controls['description'].setValue(data.description);
      this.bookForm.controls['format'].setValue(data.format);
      this.bookForm.controls['rating'].setValue(data.rating);
      this.bookForm.controls['quantity'].setValue(data.quantity);
      this.bookForm.controls['imageUrl'].setValue(data.imageUrl);
      this.bookForm.controls['active'].setValue(data.active);
      this.bookForm.controls['onSale'].setValue(data.onSale);

     this.currentUrl = data.imageUrl;
    })
    
  }

    updateBook(fileInput:any) {
      //alert(fileInput.files.length);

      if(fileInput.files.length <= 0){
        this.bookService.updateBookById(this.data.id, this.bookForm.value).subscribe((data : any) => {

      })
      this.matDialogRef.close()
      }else{
        var n = Date.now();
        const file: File = fileInput.files[0];
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
                  alert(this.bookForm.value.imageUrl);
                  this.bookService.updateBookById(this.data.id, this.bookForm.value).subscribe({
                    next: (res) => {
                      //this.message="Data Added Successfully"
                      this.matDialogRef.close()
                      this.displayAlert("Update Successful!");
                    },
                    error: (error) =>{
                      console.log(error);
                      //this.message= error.error;
                      this.matDialogRef.close()
              
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
      
      // this.bookService.updateBookById(this.data.id, this.bookForm.value).subscribe((data : any) => {

      // })
    }
    displayAlert(message:any){
      this.snackBar.open(message, "", {
        duration: 2000,panelClass:['mat-toolbar','mat-primary'],
      });
    }

    




}


