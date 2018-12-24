import { Injectable } from '@angular/core';
import { OktaAuthService } from '@okta/okta-angular';
import * as OktaSignIn from '@okta/okta-signin-widget';
import { Observable, ReplaySubject } from 'rxjs';
import { Router } from '@angular/router';

@Injectable()
export class UserAuthService {
    
    signIn = new OktaSignIn({
        baseUrl: 'https://dev-193618.oktapreview.com',
        clientId: '0oafq6phv12Mm9wPO0h7',
        redirectUri: 'http://localhost:4200/implicit/callback',
        idps:[
              { type: 'google', id: '0oagd0ibny90jyqOw0h7' },
              { type: 'linkedin', id: '0oaglfby91NfRNB1U0h7'}
            ],
        idpDisplay: "PRIMARY",
        i18n: {
            en: {
                'primaryauth.title': 'Sign In With',
                'socialauth.google.label': 'Google',
                'socialauth.linkedin.label': 'Linkedin'
            }
        }
    });
    
    constructor(private oktaService: OktaAuthService) {}
    
    showLogin() {
        this.signIn.renderEl({el: '#okta-signin-container'}, (response) => {
            if(response.status === 'SUCCESS'){
                this.signIn.tokenManager.add('idToken', response);
                this.oktaService.loginRedirect('/', { sessionToken: response.sessionToken});
            }
        },
        (err) => {
            throw err;
        });
    }
    
    get tokenAsUser() {
        const token = this.signIn.tokenManager.get('idToken');
        return token.claims.email;
    }
}