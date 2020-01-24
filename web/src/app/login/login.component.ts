import { Component, OnInit } from '@angular/core';
import { OktaAuthService } from '@okta/okta-angular';
import * as OktaSignIn from '@okta/okta-signin-widget/dist/js/okta-sign-in.min';
import { oktaConfig } from '../shared/config/okta-confiq';

@Component( {
	selector: 'app-secure',
	template: `
     <div *ngIf="!isAuthenticated" id="okta-signin-container" >
     </div>`
} )

export class LoginComponent {
	isAuthenticated: boolean;
  signIn = new OktaSignIn( {
    baseUrl: 'https://dev-193618.oktapreview.com',
    clientId: oktaConfig.clientId,
    redirectUri: oktaConfig.redirectUri,
    authParams: {
      issuer: oktaConfig.issuer,
      scopes: oktaConfig.scopes,
      responseType: ['id_token', 'token'],
      display: 'page'
    },
    i18n: {
      en: {
        'primaryauth.title': 'Sign in to iPlusplus Social Coding',
      }
    }
  } );

	constructor( private oktaService: OktaAuthService ) {
		this.oktaService.$authenticationState.subscribe(( isAuthenticated: boolean ) => this.isAuthenticated = isAuthenticated );
	}

	async ngOnInit() {
		this.isAuthenticated = await this.oktaService.isAuthenticated();

		this.signIn.renderEl( { el: '#okta-signin-container' }, ( response ) => {
			if ( response.status === 'SUCCESS' ) {
				this.oktaService.loginRedirect( '/', { sessionToken: response.sessionToken } );
			}
		},
			( err ) => {
				throw err;
			} );
	}
}