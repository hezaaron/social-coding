import { Component, OnInit } from '@angular/core';
import { ExamService } from "src/app/exam/service/exam.service";
import { Observable } from "rxjs";

@Component({
  selector: 'app-exam-list',
  template:`
      <div class="container">
        <h4 class="text-sm-center text-info">Exams List</h4>
        <div class="py-sm-5 px-sm-4 mt-4">
            <div *ngIf="!exams">Loading...</div>
            <div style="border-bottom: 1px solid #C6C6C6" *ngFor="let exam of exams | async; trackBy: trackById"> 
                <h6><a [routerLink]="[exam.id]">{{exam.name}}</a></h6>
                <p>{{exam.description}}</p>
            </div>
        </div>
    </div>`
})
export class ExamListComponent implements OnInit {
  exams: Observable<Array<any>>;

  constructor(private examService: ExamService) { }
  
  trackById(index, exam) {
      return exam.id;
  }

  async ngOnInit() {
      this.exams = this.examService.getExams();
  }

}
