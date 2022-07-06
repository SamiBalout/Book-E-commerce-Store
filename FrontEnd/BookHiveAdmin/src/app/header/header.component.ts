import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import {RouterService} from '../services/router.service'

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  opened = false
  showFiller=false

  constructor(private authService : AuthService, private route : RouterService) { }

  ngOnInit(): void {
  }


  onLogout(){
    this.authService.logoutUser();
    this.route.routeToLogin();
    
  }

  



}
