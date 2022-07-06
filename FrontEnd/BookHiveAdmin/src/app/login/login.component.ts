import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { RouterService } from '../services/router.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm : any;
  message = ''

  //id = new FormControl;
  //username = new FormControl;
 // password = new FormControl;
  //email = new FormControl;


  constructor(private formBuilder : FormBuilder, private authService : AuthService, private route : RouterService) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username : new FormControl("", [Validators.required]),
      password : new FormControl("", [Validators.required])

    })

  }

  onLogin() {
    console.log(this.loginForm)

    if(this.loginForm.username=='' || this.loginForm.password=='') {
       this.message='Please Enter Username and Password'
    }

    let tokenValue : any 
    let tokenObject : any
    this.authService.authenticateUser(this.loginForm.value).subscribe(data => {
      tokenObject = data
      tokenValue = tokenObject.token
      console.log(tokenValue)
      this.authService.setToken(tokenValue)
      this.route.routeToBookDashboard()


      
    }, err=> {
      
      this.message=this.message='Username or Password is Incorrect'
      


    
    }
    )

  }

}
