import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { OktaAuthModule } from '@okta/okta-angular';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { ExamModule } from './exam/exam.module';
import { LoginComponent } from './login/login.component';
import { AuthInterceptor } from './login/auth.interceptor';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AppRoutingModule } from './app-routing.module';
import { HttpErrorInterceptor } from './shared/interceptor/http-error.interceptor';

const redirectUri = `${environment.redirectUri}`;

const oktaConfig = {
	issuer: 'https://dev-193618.oktapreview.com/oauth2/default',
	clientId: '0oaj268wh6uRIKLy50h7',
	redirectUri: `${redirectUri}/implicit/callback`,
	scope: 'openid profile email'
}

@NgModule( {
	declarations: [
		AppComponent,
		LoginComponent,
		PageNotFoundComponent
	],
	imports: [
		BrowserModule,
		HttpClientModule,
		OktaAuthModule.initAuth( oktaConfig ),
		ExamModule,
		NgbModule.forRoot(),
		MDBBootstrapModule.forRoot(),
		ServiceWorkerModule.register( 'ngsw-worker.js', { enabled: environment.production } ),
		AppRoutingModule
	],
	providers: [
		{ provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
		{ provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true }
	],
	bootstrap: [AppComponent]
} )
export class AppModule { }
