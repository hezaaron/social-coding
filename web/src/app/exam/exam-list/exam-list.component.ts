import { Component, OnInit, Input } from '@angular/core';
import { ExamService } from '../service/exam.service';
import { Observable } from 'rxjs';
import { Quiz } from '../model/quiz';

@Component( {
	selector: 'app-exam-list',
	template: `
      <div class="container">
        <h4 class="text-sm-center text-info">Test Exams</h4>
        <div class="py-sm-5 px-sm-4 mt-3">
          <ng-container *ngIf="quizzes$ | async; else loading">
          <div style="border-bottom: 1px solid #C6C6C6" *ngFor="let quiz of quizzes$ | async; trackBy: trackById"> 
            <h6><a [routerLink]="[quiz.id]">{{quiz.name}}</a></h6>
            <p>{{quiz.description}}</p>
          </div>
          </ng-container>
          <ng-template #loading>Loading test exams...</ng-template>
        </div>
      </div>`
} )

export class ExamListComponent implements OnInit {
	@Input( 'quizzes' ) quizzes$: Observable<Quiz[]>;

	constructor( private examService: ExamService ) { }

	trackById( index, quiz ) {
		return quiz.id;
	}

	ngOnInit() {
		this.quizzes$ = this.examService.getQuizzes();
	}

}
