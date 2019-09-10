import { Component } from '@angular/core';
import { OktaAuthService, } from '@okta/okta-angular';
import { ExamService } from "./exam/service/exam.service";

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

	constructor( private oktaService: OktaAuthService, private examService: ExamService ) {
		this.oktaService.$authenticationState.subscribe(( isAuthenticated: boolean ) => this.isAuthenticated = isAuthenticated );
	}

	async ngOnInit() {
		this.isAuthenticated = await this.oktaService.isAuthenticated();
		if ( this.isAuthenticated ) {
			const userClaims = await this.oktaService.getUser();
			this.userName = userClaims.name;
			this.examService.updateUserName(this.userName);
		}
	}

	async logout() {
		await this.oktaService.logout( 'login' );
	}
}