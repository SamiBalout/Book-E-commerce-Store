import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import {MatSnackBar} from '@angular/material/snack-bar';

import{User} from '../models/user';
import { AccountService } from '../services/account.service';
import {CartService} from '../services/cart.service'

@Component({ templateUrl: 'register.component.html' })
export class RegisterComponent implements OnInit {
    form: FormGroup;
    loading = false;
    submitted = false;
    user:User

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private accountService: AccountService,
        private cartService:CartService,
        private snackBar: MatSnackBar
    ) { }

    ngOnInit() {
        this.form = this.formBuilder.group({
            firstName: ['', Validators.required],
            lastName: ['', Validators.required],
            username: ['', Validators.required],
            password: ['', [Validators.required]],
            phone: ['', Validators.required],
            address: ['', Validators.required],
            email: ['', Validators.required],
        });
    }

    get f() { return this.form.controls; }

    onSubmit() {
        this.submitted = true;
        if (this.form.invalid) {
            return;
        }

        this.loading = true;
        let user= this.form.value;
        this.accountService.register(user)
            .pipe(first())
            .subscribe({
                next: (data) => {
                    this.user=data;
                    this.cartService.addNewCartForUser(this.user.id)
                        .subscribe({
                            next:(res)=>{
                                console.log(res);
                            },
                            error:(error)=>{
                                console.log(error);
                            }
                        }  
                        )
                    //alert('Register sucessful!');
                    this.displayAlert('Register sucessful!');
                    this.router.navigate(['../login']);
                },
                error: error => {
                    this.loading = false;
                }
            });
    }

    displayAlert(message:any){
        this.snackBar.open(message, "", {
          duration: 5000,panelClass:['mat-toolbar','mat-warn'],
        });
      }
}