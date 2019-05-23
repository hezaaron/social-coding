import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ExamService } from '../service/exam.service';

@Component( {
	selector: 'app-exam-info',
	template: `
  <div class="container">
  	<ng-container *ngIf="examName; else loading">
	    <h4 class="text-sm-center text-info">{{examName}}</h4>
	    <div class="py-sm-5 px-sm-4 mt-3">
	      <p>There are five questions in this test. For each question, there is a code snippet
	  		and four possible answer options.<br/>
	  		Work out the correct answer to each question by reading the code.<br/>
	  		You have 1 minute to answer each question and you cannot go back to previous question.<br/>
	  		To start the test, click the START button.</p>
	    </div>
	  </ng-container>
    <ng-template #loading>Loading test exam...</ng-template>
    <div class="float-right d-inline-block">
			<button class="btn btn-primary text-center  mt-sm-5" (click)="startExam();">Start</button>
		</div>
  </div>`
} )
export class ExamInfoComponent implements OnInit {
	examId: number;
	examName: string;

	constructor( private route: ActivatedRoute, private router: Router, private examService: ExamService ) { }

	ngOnInit() {
		const id = +this.route.snapshot.paramMap.get( 'id' );
		if ( id ) {
			this.examId = id;
		} else {
			console.log( `Exam with id '${id}' not found, returning to list` );
			this.router.navigate( ['/'] );
		}

		this.setExamName();
	}

	setExamName(): void {
		this.examService.getExam( this.examId ).subscribe( exam => {
			this.examName = exam.name
		} );
	}

	startExam(): void {
		this.examService.updateExamId( this.examId );
		this.router.navigate( ['/exam', this.examId] );
	}

}
