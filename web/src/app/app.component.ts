import { Component } from '@angular/core';
import { OktaAuthService, } from '@okta/okta-angular';
import { QuizService } from './quiz/service/quiz.service';

@Component( {
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css']
} )
export class AppComponent {
	logo = 'assets/logo.png';
	title = 'iPlusplus';
	isAuthenticated: boolean;
	isNavbarCollapsed = true;
	userName: string;

	constructor( public oktaService: OktaAuthService, private quizService: QuizService ) {
		this.oktaService.$authenticationState.subscribe(( isAuthenticated: boolean ) => this.isAuthenticated = isAuthenticated );
	}

	async ngOnInit() {
		this.isAuthenticated = await this.oktaService.isAuthenticated();
		if ( this.isAuthenticated ) {
			const userClaims = await this.oktaService.getUser();
			this.userName = userClaims.name;
			this.quizService.updateUserName(this.userName);
		}
	}

	async logout() {
		await this.oktaService.logout( 'login' );
	}
}