import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  //tempUrl = 'http://localhost:3000/auth/v1'
  url = 'https://bookhive-api-2.herokuapp.com/admin'

  constructor(private httpClient : HttpClient) { }

  token = ''

  //Set Token
  setToken(token : any) {
    localStorage.setItem('token', token)
  }

  //Get Token
  getToken() {
    return localStorage.getItem('token')
  }

  //Delete Token
  logoutUser() {
    localStorage.removeItem('token')
  }

  
  isUserAuthenticated(token : any) {
    console.log(token);

    const headers = new HttpHeaders({

      'Content-Type': 'application/json',

      'Authorization':`Bearer ${token}`

  });

    return this.httpClient.get(this.url + '/check/isAuthenticated', {headers:headers})
  }

  authenticateUser(data : any) {
    return this.httpClient.post(this.url + `/login`, data)
  }
  




}
