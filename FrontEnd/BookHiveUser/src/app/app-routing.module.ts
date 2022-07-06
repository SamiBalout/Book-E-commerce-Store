import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

//import { AuthGuard } from './helpers/auth.guard';
import { BookComponent } from './book/book.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { CartComponent } from './cart/cart.component';
import { OrderComponent } from './order/order.component';
import { HomeComponent } from './home/home.component';
import { HorrorComponent } from './horror/horror.component';
import { OnSaleComponent } from './on-sale/on-sale.component';
import { LearningComponent } from './learning/learning.component';

const routes: Routes = [
  {path:'',component:HomeComponent},
  {path:'book',component:BookComponent},
  {path:'login',component:LoginComponent},
  {path:'register',component:RegisterComponent},
  {path:'cart',component:CartComponent},
  {path:'order',component:OrderComponent},
  {path:'home',component:HomeComponent},
  {path:'horror',component:HorrorComponent},
  {path: 'onsale', component:OnSaleComponent},
  {path: 'learning', component:LearningComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes,{ onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
