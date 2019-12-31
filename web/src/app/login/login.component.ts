import { Component, OnInit } from '@angular/core';
import { OktaAuthService } from '@okta/okta-angular';
import * as OktaSignIn from '@okta/okta-signin-widget/dist/js/okta-sign-in.min';
import { environment } from '../../environments/environment';

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
    authParams: {
      pkce: true
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