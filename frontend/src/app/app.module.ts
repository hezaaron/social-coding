import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { LoginComponent } from './login.component';
import { ResultComponent } from './result.component'
import { OktaAuthModule, OktaAuthGuard, OktaCallbackComponent } from '@okta/okta-angular';
import { HighlightModule } from 'ngx-highlightjs';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationPopoverModule } from 'angular-confirmation-popover';
import { MDBBootstrapModule } from 'angular-bootstrap-md';

import { AuthInterceptor } from './shared/okta/auth.interceptor';
import { ExamListComponent } from './exam-list/exam-list.component';
import { ExamComponent } from './exam/exam.component';
import { FormatTimePipe } from './shared/pipes/format-time.pipe';

export function onAuthRequired({oktaAuth, router}) {
    router.navigate(['/login']);
}

const oktaConfig = {
    issuer: 'https://dev-193618.oktapreview.com/oauth2/default',
    clientId: '0oaj268wh6uRIKLy50h7',
    redirectUri: 'http://localhost:4200/implicit/callback',
    scope: 'openid profile email'
}

const appRoutes: Routes = [
    {path: '', redirectTo: '/testexams', pathMatch: 'full'},
    {path: 'testexams', component: ExamListComponent, canActivate: [ OktaAuthGuard ], data: {onAuthRequired}},
    {path: 'testexams/:id', component: ExamComponent, canActivate: [ OktaAuthGuard ], data: {onAuthRequired}},
    {path: 'resultstat/:id', component: ResultComponent, canActivate: [ OktaAuthGuard ], data: {onAuthRequired}},
    {path: 'login', component: LoginComponent},
    {path: 'implicit/callback', component: OktaCallbackComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ExamListComponent,
    ExamComponent,
    ResultComponent,
    FormatTimePipe
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    OktaAuthModule.initAuth(oktaConfig),
    HighlightModule.forRoot(),
    FormsModule,
    ReactiveFormsModule,
    NgbModule.forRoot(),
    ConfirmationPopoverModule.forRoot({confirmButtonType: 'danger'}),
    MDBBootstrapModule.forRoot()
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
