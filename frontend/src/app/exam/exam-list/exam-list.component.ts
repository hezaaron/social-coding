import { Component, OnInit, Input } from '@angular/core';
import { ExamService } from "src/app/exam/service/exam.service";
import { Observable } from "rxjs";
import { Exam } from "src/app/exam/model/exam";

@Component( {
	selector: 'app-exam-list',
	template: `
      <div class="container">
        <h4 class="text-sm-center text-info">Exams List</h4>
        <div class="py-sm-5 px-sm-4 mt-4">
          <div *ngIf="isLoading">{{loadingExams}}</div>
          <div style="border-bottom: 1px solid #C6C6C6" *ngFor="let exam of exams | async; trackBy: trackById"> 
            <h6><a [routerLink]="[exam.id]">{{exam.name}}</a></h6>
            <p>{{exam.description}}</p>
          </div>
        </div>
    </div>`
} )

export class ExamListComponent implements OnInit {
	public loadingExams = 'Loading test exams...';
	public isLoading = true;
	@Input( 'exams' ) exams: Observable<Exam[]>;

	constructor( private examService: ExamService ) { }

	trackById( index, exam ) {
		return exam.id;
	}

	ngOnInit() {
		this.getExams();
	}

	public getExams(): void {
		this.isLoading = false;
		this.exams = this.examService.getExams();
	}

}
