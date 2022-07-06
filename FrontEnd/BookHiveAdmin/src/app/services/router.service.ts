import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RouterService {

  constructor(private router : Router) { }

  routeToBookDashboard() {
    return this.router.navigate(['admin/bookdashboard'])
  }

  routeToLogin() {
    return this.router.navigate(['admin/login'])
  }

  routeToLocation(path : any) {
    return this.router.navigate([path])
  }
}
