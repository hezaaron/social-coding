import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { QuizService } from '../service/quiz.service';
import { SharedDataService } from '../service/shared-data.service';

@Component( {
	selector: 'app-quiz-info',
	template: `
  <div class="container">
  	<ng-container *ngIf="quizName; else loading">
	    <h4 class="text-sm-center text-info">{{quizName}}</h4>
	    <div class="py-sm-5 px-sm-4 mt-3">
	      <p>There are five questions in this test. For each question, there is a code snippet
	  		and four possible answer options.<br/>
	  		Work out the correct answer to each question by reading the code.<br/>
	  		You have 1 minute to answer each question and you cannot go back to previous question.<br/>
	  		To start the test, click the START button.</p>
	    </div>
	  </ng-container>
    <ng-template #loading>Loading quiz...</ng-template>
    <div class="float-right d-inline-block">
			<button class="btn btn-primary text-center  mt-sm-5" (click)="startQuiz();">Start</button>
		</div>
  </div>`
} )
export class QuizInfoComponent implements OnInit {
	quizId: number;
	quizName: string;

	constructor( private route: ActivatedRoute, private router: Router, private quizService: QuizService,
	             private sharedDataService: SharedDataService ) { }

	ngOnInit() {
		const id = +this.route.snapshot.paramMap.get( 'id' );
		if ( id ) {
			this.quizId = id;
		} else {
			console.log( `Quiz with id '${id}' not found, returning to list` );
			this.router.navigate( ['/'] );
		}

		this.setQuizName();
	}

	setQuizName(): void {
		this.quizService.getQuiz( this.quizId ).subscribe( quiz => {
			this.quizName = quiz.name
		} );
	}

	startQuiz(): void {
		this.sharedDataService.updateQuizId( this.quizId );
		this.router.navigate( ['/quiz', this.quizId] );
	}

}
