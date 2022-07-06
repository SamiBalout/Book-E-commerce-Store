import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

import { AccountService } from '../services/account.service';


@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {

    isUserAuthenticated=false

    constructor(
        private router: Router,
        private accountService: AccountService
    ) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){
        this.accountService.isUserAuthenticated()
            .subscribe({
                next:(res)=>{
                    this.isUserAuthenticated=true;
                },
                error:(error)=>{
                    this.isUserAuthenticated=false;
                    alert('Please login to view!');
                    this.router.navigate(['/login']);  
                }
            })
        return this.isUserAuthenticated;
    }
}