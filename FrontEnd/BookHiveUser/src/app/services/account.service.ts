import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

import { environment } from '../../environments/environment';
import { User } from '../models/user';

@Injectable({ providedIn: 'root' })
export class AccountService {

    url = `${environment.url}/user`;

    constructor
    (
        private httpclient:HttpClient     
    ) 
    {}

    login(username: string, password: string){
        return this.httpclient.post( `${this.url}/login`, { username, password });           
    }

    isUserAuthenticated() {
        if(this.getUserFromLocalStorge().token){
            return true;
        }else{
            return false;
        }
        /*
        return this.httpclient.post(`${environment.apiUrl}:${environment.userPort}/user/check/isAuthenticated`,
          {headers: new HttpHeaders().set( 'Content-Type','application/json').set('Authorization', `Bearer ${this.getUserFromLocalStorge().token}`)}
        )*/
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(user: User) {
        return this.httpclient.post(`${this.url}/register`,user);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error);
        return Promise.reject(error.message || error);
    }

    public getUserFromLocalStorge(){
        return JSON.parse(localStorage.getItem('user')|| '{}');
    }

    UpdatePasswordById(){

    }

    UpdateContactDetailsById(){

    }

    GetContactDetails(id:any){
        return this.httpclient.get(`${this.url}/getUser/${id}`)

    }
}