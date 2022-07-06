import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Route, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { RouterService } from '../services/router.service';

@Injectable({

  providedIn: 'root'

})

export class AuthGuard implements CanActivate {

  flag = false

  constructor(private authService : AuthService, private route : RouterService) {}

  canActivate(

    route: ActivatedRouteSnapshot,

    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    
      this.authService.isUserAuthenticated(this.authService.getToken()).subscribe({
        next: (res) => {
          this.flag = true; 
          this.route.routeToLocation(route.routeConfig?.path?.toString());
          console.log
        },
        error: (error) =>{
          console.log(error.error);
          this.flag = false; 
          this.route.routeToLogin();
          console.log

        }
      })

      //return this.flag
      return this.flag;
      
      

  }



}