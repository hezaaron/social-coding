import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { LoginComponent } from './login.component';
import { ResultComponent } from './result.component'
import { OktaAuthService, } from './shared/okta/okta.service';
import { OktaAuthGuard, } from './shared/okta/okta.guard';
import { HighlightModule } from 'ngx-highlightjs';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationPopoverModule } from 'angular-confirmation-popover';
import { MDBBootstrapModule } from 'angular-bootstrap-md';

import { ExamService } from './shared/exam/exam.service';
import { AuthInterceptor } from './shared/okta/auth.interceptor';
import { ExamListComponent } from './exam-list/exam-list.component';
import { ExamComponent } from './exam/exam.component';
import { FormatTimePipe } from './shared/pipes/format-time.pipe';


const appRoutes: Routes = [
    {path: 'testexams', component: ExamListComponent, canActivate: [ OktaAuthGuard ]},
    {path: '', redirectTo: '/testexams', pathMatch: 'full'},
    {path: 'testexams/:id', component: ExamComponent, canActivate: [ OktaAuthGuard ]},
    {path: 'resultstat/:id', component: ResultComponent, canActivate: [ OktaAuthGuard ]},
    {path: 'login', component: LoginComponent}
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
    HighlightModule.forRoot(),
    FormsModule,
    ReactiveFormsModule,
    NgbModule.forRoot(),
    ConfirmationPopoverModule.forRoot({confirmButtonType: 'danger'}),
    MDBBootstrapModule.forRoot()
  ],
  providers: [
    ExamService, OktaAuthService, OktaAuthGuard,
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
