import { Component } from '@angular/core';
import { OktaAuthService, } from '@okta/okta-angular';
import { Subscription } from 'rxjs';
import { SharedDataService } from './coding-quiz/service/shared-data.service';

@Component( {
	selector: 'app-root',
	templateUrl: './app.component.html'
} )
export class AppComponent {
	logo = 'assets/logo.png';
	title = 'Social Coding';
	isAuthenticated: boolean;
	isNavbarCollapsed = true;
	username: string;
	private subscription: Subscription;

	constructor( public oktaService: OktaAuthService, private sharedDataService: SharedDataService ) {
		this.oktaService.$authenticationState.subscribe(( isAuthenticated: boolean ) => this.isAuthenticated = isAuthenticated );
	}

	async ngOnInit() {
		this.isAuthenticated = await this.oktaService.isAuthenticated();
	    this.subscription = this.sharedDataService.username.subscribe( name => {
            this.username = name;
	    },
        error => console.error( error ) );
	}

	async logout() {
		await this.oktaService.logout( 'login' );
	}
	
	ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}