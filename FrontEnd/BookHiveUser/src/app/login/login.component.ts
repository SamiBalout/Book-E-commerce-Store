import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';

import { AccountService } from '../services/account.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  hide = true;
  message=''

  constructor(
  
    private formBuilder: FormBuilder,  
    private router: Router,
    private accountService : AccountService,
    private snackBar:MatSnackBar
    
    ) {}

  ngOnInit(): void {
    
    this.loginForm = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
    });
    
  }


  onSubmit() {

    this.submitted = true;
    this.loading=true;

    this.accountService.login(this.loginForm.value.username, this.loginForm.value.password)
      .subscribe(
        data=>{
          localStorage.setItem('user',JSON.stringify(data));
          //alert('Login sucessful!');
          this.displayAlert('Login Sucessful!')
          this.router.navigate([''])
            .then(()=>window.location.reload());
        },
        err=>{
          this.displayAlert('Username or password is incorrect');
          //alert('Username or password is incorrect');
        }
      )
  }

  displayAlert(message:any){
    this.snackBar.open(message, "", {
      duration: 2000,panelClass:['mat-toolbar','mat-primary'],
    });
  }
  
}
