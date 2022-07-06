import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  clientHeight: number;
  title = 'book_Hive';

  constructor() {
    this.clientHeight = window.innerHeight; 
 }
}
