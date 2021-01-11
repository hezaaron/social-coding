import { Component, OnInit, Input } from '@angular/core';
import { OktaAuthService } from '@okta/okta-angular';
import { QuizService } from '../service/quiz.service';
import { SharedDataService } from '../service/shared-data.service';
import { Observable } from 'rxjs';
import { Quiz } from '../model/quiz';

@Component( {
	selector: 'app-quiz-list',
	template: `
      <div class="container">
        <h4 class="text-sm-center text-info">Social Coding</h4>
        <div class="py-sm-5 px-sm-4 mt-3">
          <ng-container *ngIf="quizzes$ | async; else loading">
          <div style="border-bottom: 1px solid #C6C6C6" *ngFor="let quiz of quizzes$ | async; trackBy: trackById"> 
            <h6><a routerLink="{{quiz.id}}/instructions">{{quiz.name}}</a></h6>
            <p>{{quiz.description}}</p>
          </div>
          </ng-container>
          <ng-template #loading>Loading Quizzes...</ng-template>
        </div>
      </div>`
} )

export class QuizListComponent implements OnInit {
	@Input( 'quizzes' ) quizzes$: Observable<Quiz[]>;
	isAuthenticated: boolean;

	constructor( private quizService: QuizService, private sharedDataService: SharedDataService,
	             public oktaService: OktaAuthService ) {
	  this.oktaService.$authenticationState.subscribe(( isAuthenticated: boolean ) => this.isAuthenticated = isAuthenticated );
	}

	trackById( index, quiz ) {
		return quiz.id;
	}

	async ngOnInit() {
		this.quizzes$ = this.quizService.getQuizzes();
		this.isAuthenticated = await this.oktaService.isAuthenticated();
    
		if ( this.isAuthenticated ) {
            const userClaims = await this.oktaService.getUser();
            this.sharedDataService.updateUsername(userClaims.name);
        }
	}
}
