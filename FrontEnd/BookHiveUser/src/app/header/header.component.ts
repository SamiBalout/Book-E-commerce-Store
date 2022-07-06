import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, ActivatedRoute } from '@angular/router';

import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isUserAuthenticated:boolean

  constructor(
    private router: Router,
    private accountService: AccountService,
    private snackBar:MatSnackBar
  ) { }

  ngOnInit(): void {
    this.isUserAuthenticatedFunction();
  }

  isUserAuthenticatedFunction(){
    this.isUserAuthenticated=this.accountService.isUserAuthenticated();
  
    /*
    this.accountService.isUserAuthenticated()
            .subscribe({
                next:(res)=>{
                    this.isUserAuthenticated=true;
                    console.log(this.isUserAuthenticated);
                },
                error:(error)=>{
                    this.isUserAuthenticated=false;
                    console.log(this.isUserAuthenticated);
                }
            })*/
  }

  logout(){
    this.accountService.logout();
    if(!localStorage.getItem('user')){
      //alert('Logout sucessful!');
      this.displayAlert('Logout Sucessful!');
      this.router.navigate(['book'])
            .then(()=>window.location.reload());//refresh page after change
    }
  }

  statusChange(){
    
  }
  displayAlert(message:any){
    this.snackBar.open(message, "", {
      duration: 2000,panelClass:['mat-toolbar','mat-primary'],
    });
  }

}
