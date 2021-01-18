import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { QuizService } from '../service/quiz.service';
import { SharedDataService } from '../service/shared-data.service';
import { Report } from '../model/report';


@Component( {
	selector: 'app-quiz-results',
	template: `
      <div class="container">
        <ng-container *ngIf="report; else loading">
          <h5 class="text-sm-center blue-grey-text">{{report.title}}</h5>
          <div class="mt-4">
            <table class="table">
              <tr><td>Start:</td><td>{{report.startTime}}</td></tr>
              <tr><td>Finish:</td><td>{{report.finishTime}}</td></tr>
              <tr><td class="text-nowrap">Number of Questions:</td><td>{{report.questionCount}}</td></tr>
              <tr><td>Score:</td><td [ngClass]="{'text-success': report.grade >= 50, 'text-danger': report.grade < 50}">{{report.grade}}</td></tr>
              <tr><td>Max Score:</td><td>{{report.maxGrade}}</td></tr> 
            </table>
          </div>
          <div class="float-right d-inline-block">
            <button class="btn mt-sm-3" (click)="quizzes()">Return to Quiz List</button>
          </div>
		</ng-container>
		<ng-template #loading>Loading results...</ng-template>
      </div>`
} )
export class QuizResultComponent implements OnInit {
	report: Report;
	resultId: number;
	private subscription: Subscription;

	constructor( private quizService: QuizService, private sharedDataService: SharedDataService,
                 private router: Router ) { }

	ngOnInit() {
		this.subscription = this.sharedDataService.resultId.subscribe( value => {
			this.resultId = value;
		},
		error => console.error( error ) );

		this.quizService.getReport( this.resultId ).subscribe( report => {
			this.report = report;
		} );
	}
    
    quizzes() {
        this.router.navigate( ['/quizzes'] );
    }

	ngOnDestroy() {
		this.subscription.unsubscribe();
	}
}
