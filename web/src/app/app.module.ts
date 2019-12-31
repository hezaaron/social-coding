import { BrowserModule } from '@angular/platform-browser';
import { NgModule, NO_ERRORS_SCHEMA} from '@angular/core';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { MDBBootstrapModule } from 'angular-bootstrap-md';

import { AppComponent } from './app.component';
import { OKTA_CONFIG, OktaAuthModule } from '@okta/okta-angular';
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
    clientId: '0oanjpbibbdsu3Go60h7',
    redirectUri: `${redirectUri}/implicit/callback`,
    pkce: true
}

@NgModule( {
	declarations: [
		AppComponent,
		LoginComponent,
		PageNotFoundComponent
	],
	imports: [
		BrowserModule.withServerTransition({ appId: 'serverApp' }),
		HttpClientModule,
		OktaAuthModule,
		ExamModule,
		ServiceWorkerModule.register( 'ngsw-worker.js', { enabled: environment.production } ),
		AppRoutingModule,
		MDBBootstrapModule.forRoot()
	],
	schemas: [ NO_ERRORS_SCHEMA ],
	providers: [
	  { provide: OKTA_CONFIG, useValue: oktaConfig },
		{ provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
		{ provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true }
	],
	bootstrap: [AppComponent]
} )
export class AppModule { }
