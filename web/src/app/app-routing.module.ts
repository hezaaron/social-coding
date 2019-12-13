import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { OktaCallbackComponent } from '@okta/okta-angular';

const routes: Routes = [{
    path: 'login', component: LoginComponent
  }, {
    path: 'implicit/callback', component: OktaCallbackComponent
  }/*, {
    path: '', pathMatch: 'full', redirectTo: '/home'
}*/];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
