import { Component } from '@angular/core';
import { OktaAuthService, } from '@okta/okta-angular';
import { Subscription } from 'rxjs';
import { QuizService } from './quiz/service/quiz.service';
import { SharedDataService } from './quiz/service/shared-data.service';

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
	username: string;
	private subscription: Subscription;

	constructor( public oktaService: OktaAuthService, private quizService: QuizService,
	             private sharedDataService: SharedDataService ) {
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