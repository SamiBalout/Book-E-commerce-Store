import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { inject } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AngularFireModule } from '@angular/fire/compat';
import { AngularFireStorage, AngularFireStorageModule } from '@angular/fire/compat/storage';
import { FormBuilder } from '@angular/forms';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { environment } from 'src/environments/environment.prod';
import { AppRoutingModule } from '../app-routing.module';
import { BookService } from '../services/book.service';

import { UpdateBookComponent } from '../update-book/update-book.component';

// describe('UpdateBookComponent', () => {
//   let component: UpdateBookComponent;
//   let fixture: ComponentFixture<UpdateBookComponent>;

//   beforeEach(async () => {
//     await TestBed.configureTestingModule({
//       declarations: [ UpdateBookComponent ],
//       imports: [ HttpClientTestingModule, //RouterTestingModule, BrowserModule,
//         //AppRoutingModule,
//         //BrowserAnimationsModule,
//         // ReactiveFormsModule,
//         // FormsModule,
//         // MatToolbarModule,
//         // MatIconModule,
//         // MatCardModule,
//         // MatDividerModule,
//         // MatFormFieldModule,
//         // FlexLayoutModule,
//         // MatInputModule,
//         // MatButtonModule,
//         // HttpClientModule,
//         // MatTableModule,
//         // MatPaginatorModule,
//         MatDialogModule,
//         // MatSidenavModule,
//         // MatListModule,
//         // MatSelectModule,
//         // MatExpansionModule,
//         // AngularFireStorageModule,
//         AngularFireModule.initializeApp(environment.firebaseConfig, "cloud"),
//         // MatMenuModule,
//         // MatBadgeModule,
//         MatSnackBarModule,
//         MAT_DIALOG_DATA,
//         FormBuilder,
//         AngularFireStorageModule
        
        
//       ],
//       providers: [BookService, HttpClientModule, MatSnackBarModule, MatDialogRef, FormBuilder]

//     })
//     .compileComponents();

//   });

//   beforeEach(() => {
//     fixture = TestBed.createComponent(UpdateBookComponent);
//     component = fixture.componentInstance;
//     fixture.detectChanges();
//   });

//   // it('updateBook() method should exist', () => {
//   //   expect(component.updateBook).toBeTruthy();
//   // });


  
// });
