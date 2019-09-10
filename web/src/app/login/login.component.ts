import { Component, OnInit } from '@angular/core';
import { OktaAuthService } from '@okta/okta-angular';
import * as OktaSignIn from '@okta/okta-signin-widget/dist/js/okta-sign-in.min';
import { environment } from '../../environments/environment';

const redirectUri = `${environment.redirectUri}`;

@Component( {
	selector: 'app-secure',
	template: `
		 <div *ngIf="!isAuthenticated" id="okta-signin-container" >
       <div class="intro">
         <h5 class="text-sm-center">iplusplus Exam Maker</h5>
         <p class="mt-2">iplusplus exams are multiple choice coding questions that will help you 
            prepare for your technical interview and make a good impression.</p>
       </div>
	   </div>`
} )

export class LoginComponent {
	isAuthenticated: boolean;
	signIn = new OktaSignIn( {
		baseUrl: 'https://dev-193618.oktapreview.com',
		clientId: '0oaj268wh6uRIKLy50h7',
		redirectUri: `${redirectUri}/implicit/callback`,
		authParams: {
			responseType: ['id_token', 'token'],
			issuer: 'https://dev-193618.oktapreview.com/oauth2/default',
			display: 'page',
			scopes: ['openid', 'profile', 'email']
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