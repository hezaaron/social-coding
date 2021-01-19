import { BrowserModule } from '@angular/platform-browser';
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { OKTA_CONFIG, OktaAuthModule } from '@okta/okta-angular';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { QuizModule } from './coding-quiz/coding-quiz.module';
import { LoginComponent } from './login/login.component';
import { AuthInterceptor } from './login/auth.interceptor';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AppRoutingModule } from './app-routing.module';
import { HttpErrorInterceptor } from './shared/interceptor/http-error.interceptor';
import { oktaConfig } from './shared/config/okta-confiq';
import { LeaderBoardComponent } from './gamification/leaderboard/leaderboard.component';
import { LeaderBoardService } from './gamification/leaderboard/leaderboard.service';
import { UserStatsComponent } from './gamification/statistics/user-stats.component';
import { UserStatsService } from './gamification/statistics/user-stats.service';

@NgModule( {
	declarations: [
		AppComponent,
		LoginComponent,
		PageNotFoundComponent,
        UserStatsComponent,
        LeaderBoardComponent
	],
	imports: [
		BrowserModule,
		HttpClientModule,
		OktaAuthModule,
		QuizModule,
		NgbModule.forRoot(),
		MDBBootstrapModule.forRoot(),
		ServiceWorkerModule.register( 'ngsw-worker.js', { enabled: environment.production } ),
		AppRoutingModule
	],
	providers: [
        LeaderBoardService, UserStatsService,
	    { provide: OKTA_CONFIG, useValue: oktaConfig },
		{ provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
		{ provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true }
	],
	bootstrap: [AppComponent]
} )
export class AppModule { }
