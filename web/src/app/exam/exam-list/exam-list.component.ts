import { Component, OnInit, Input } from '@angular/core';
import { ExamService } from '../service/exam.service';
import { Observable } from 'rxjs';
import { Exam } from '../model/exam';

@Component( {
	selector: 'app-exam-list',
	template: `
      <div class="container">
        <h4 class="text-sm-center text-info">Test Exams</h4>
        <div class="py-sm-5 px-sm-4 mt-3">
          <ng-container *ngIf="exams$ | async; else loading">
          <div style="border-bottom: 1px solid #C6C6C6" *ngFor="let exam of exams$ | async; trackBy: trackById"> 
            <h6><a [routerLink]="[exam.id]">{{exam.name}}</a></h6>
            <p>{{exam.description}}</p>
          </div>
          </ng-container>
          <ng-template #loading>Loading test exams...</ng-template>
        </div>
      </div>`
} )

export class ExamListComponent implements OnInit {
	@Input( 'exams' ) exams$: Observable<Exam[]>;

	constructor( private examService: ExamService ) { }

	trackById( index, exam ) {
		return exam.id;
	}

	ngOnInit() {
		this.exams$ = this.examService.getExams();
	}

}
