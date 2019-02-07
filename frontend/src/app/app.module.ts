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
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

export function onAuthRequired({oktaAuth, router}) {
    router.navigate(['/login']);
}

const redirectUri = `${environment.redirectUri}`;

const oktaConfig = {
    issuer: 'https://dev-193618.oktapreview.com/oauth2/default',
    clientId: '0oaj268wh6uRIKLy50h7',
    redirectUri: `${redirectUri}/implicit/callback`,
    scope: 'openid profile email'
}

const appRoutes: Routes = [{
        path: 'login', component: LoginComponent
        },{
        path: '', pathMatch: 'full', redirectTo: '/testexams'
        }, {
        path: 'testexams', component: ExamListComponent, canActivate: [ OktaAuthGuard ], data: {onAuthRequired}
        }, {
        path: 'testexams/:id', component: ExamPaperComponent, canActivate: [ OktaAuthGuard ], data: {onAuthRequired}
        }, {
        path: 'resultstat/:id', component: ExamResultsComponent, canActivate: [ OktaAuthGuard ], data: {onAuthRequired}
        }, {
          path: 'implicit/callback', component: OktaCallbackComponent
        }, {
        path: '**', component: PageNotFoundComponent
}]

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    OktaAuthModule.initAuth(oktaConfig),
    ExamModule,
    NgbModule.forRoot(),
    MDBBootstrapModule.forRoot(),
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: environment.production })
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
