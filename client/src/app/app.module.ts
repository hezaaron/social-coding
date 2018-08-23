import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { LoginComponent } from './login.component';
import { OktaAuthModule, OktaAuthGuard, OktaCallbackComponent } from '@okta/okta-angular';
import { HighlightModule } from 'ngx-highlightjs';

import { ExamService } from './shared/exam/exam.service';
import { AuthInterceptor } from './shared/okta/auth.interceptor';
import { ExamListComponent } from './exam-list/exam-list.component';
import { ExamComponent } from './exam/exam.component';


export function onAuthRequired({oktaAuth, router}) {
    router.navigate(['/login']);
}

const oktaConfig = {
    issuer: 'https://dev-193618.oktapreview.com/oauth2/default',
    clientId: '0oafq6phv12Mm9wPO0h7',
    redirectUri: 'http://localhost:4200/implicit/callback'
}

const appRoutes: Routes = [
    {
        path: 'testexams', component: ExamListComponent, canActivate: [ OktaAuthGuard],
        data: {
            onAuthRequired
        }
    },
    {
        path: '', redirectTo: 'testexams', pathMatch: 'full'
    },
    {
        path: 'testexams/:id', component: ExamComponent, canActivate: [ OktaAuthGuard],
        data: {
            onAuthRequired
        }
    },
    {
        path: 'login', component: LoginComponent
    },
    {
        path: 'implicit/callback', component: OktaCallbackComponent
    }
]

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ExamListComponent,
    ExamComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    OktaAuthModule.initAuth(oktaConfig),
    HighlightModule.forRoot({theme: 'grayscale'})
  ],
  providers: [ExamService, {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
