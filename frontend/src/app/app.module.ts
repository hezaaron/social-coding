import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { OktaAuthModule, OktaAuthGuard, OktaCallbackComponent } from '@okta/okta-angular';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { ExamListComponent } from "src/app/exam/exam-list/exam-list.component";
import { ExamModule } from "src/app/exam/exam.module";
import { LoginComponent } from "src/app/login/login.component";
import { AuthInterceptor } from "src/app/login/auth.interceptor";
import { ExamPaperComponent } from "src/app/exam/exam-paper/exam-paper.component";
import { ExamResultsComponent } from "src/app/exam/exam-results/exam-results.component";

export function onAuthRequired({oktaAuth, router}) {
    router.navigate(['/login']);
}

const oktaConfig = {
    issuer: 'https://dev-193618.oktapreview.com/oauth2/default',
    clientId: '0oaj268wh6uRIKLy50h7',
    redirectUri: 'http://localhost:4200/implicit/callback',
    scope: 'openid profile email'
}

const appRoutes: Routes = [{
        path: 'login', component: LoginComponent
        }, {
        path: 'implicit/callback', component: OktaCallbackComponent
        },{
        path: '', pathMatch: 'full', redirectTo: '/testexams'
        }, {
        path: 'testexams', component: ExamListComponent, canActivate: [ OktaAuthGuard ], data: {onAuthRequired}
        }, {
        path: 'testexams/:id', component: ExamPaperComponent, canActivate: [ OktaAuthGuard ], data: {onAuthRequired}
        }, {
        path: 'resultstat/:id', component: ExamResultsComponent, canActivate: [ OktaAuthGuard ], data: {onAuthRequired}
}]

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    OktaAuthModule.initAuth(oktaConfig),
    ExamModule,
    NgbModule.forRoot(),
    MDBBootstrapModule.forRoot()
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
