import { Component } from '@angular/core';
import { OktaAuthService, } from '@okta/okta-angular';

@Component( {
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css']
} )
export class AppComponent {
	logo = 'assets/logo.png';
	title = 'iplusplus';
	isAuthenticated: boolean;
	isNavbarCollapsed = true;
	userName: string;

	constructor( private oktaService: OktaAuthService ) {
		this.oktaService.$authenticationState.subscribe(( isAuthenticated: boolean ) => this.isAuthenticated = isAuthenticated );
	}

	async ngOnInit() {
		this.isAuthenticated = await this.oktaService.isAuthenticated();
		if ( this.isAuthenticated ) {
			const userClaims = await this.oktaService.getUser();
			this.userName = userClaims.name;
		}
	}

	async logout() {
		await this.oktaService.logout( 'login' );
	}
}