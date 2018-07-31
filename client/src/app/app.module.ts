import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { ProtectedComponent } from './protected.component';
import { LoginComponent } from './login.component';
import { OktaAuthModule, OktaAuthGuard, OktaCallbackComponent } from '@okta/okta-angular';


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
        path: '',
        component: ProtectedComponent,
        canActivate: [ OktaAuthGuard],
        data: {
            onAuthRequired
        }
    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'implicit/callback',
        component: OktaCallbackComponent
    }
]

@NgModule({
  declarations: [
    AppComponent,
    ProtectedComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    OktaAuthModule.initAuth(oktaConfig)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
