import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookdashboardComponent } from './bookdashboard/bookdashboard.component';
import { FooterComponent } from './footer/footer.component';
import { AuthGuard } from './guards/auth.guard';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { OrdersComponent } from './orders/orders.component';
import { UpdateBookComponent } from './update-book/update-book.component';

const routes: Routes = [

  //Add CanActivate
  {path : "admin/login", component : LoginComponent},
  {path : "header", component : HeaderComponent, canActivate : [AuthGuard]},
  {path : "home", component : HomeComponent, canActivate : [AuthGuard]},
  {path : 'admin/bookdashboard', component : BookdashboardComponent, canActivate : [AuthGuard]},
  {path : 'admin/orders', component : OrdersComponent, canActivate : [AuthGuard]},
  {path : 'update-book', component : UpdateBookComponent, canActivate : [AuthGuard]},
  {path : 'footer', component : FooterComponent, canActivate : [AuthGuard] }
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
