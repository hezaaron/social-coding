import { Component, OnInit } from '@angular/core';
import { Observable } from "rxjs";
import { ExamService } from "src/app/exam/service/exam.service";

@Component({
  selector: 'app-exam-results',
  template: `
      <div class="container">
          <div *ngIf="!stats">Loading...</div>
          <div *ngIf="stats">
              <h5 class="text-sm-center blue-grey-text">{{stats.title}}</h5>
              <div class="mt-4">
                  <table class="table">
                      <tr><td>Start:</td><td>{{stats.startTime}}</td></tr> 
                      <tr><td>Finish:</td><td>{{stats.finishTime}}</td></tr>
                      <tr><td class="text-nowrap">Number of Questions:</td><td>{{stats.questionCount}}</td></tr>
                      <tr><td>Score:</td><td [ngClass]="{'text-success': stats.grade >= 50, 'text-danger': stats.grade < 50}">{{stats.grade}}</td></tr>
                      <tr><td>Max Score:</td><td>{{stats.maxGrade}}</td></tr> 
                  </table>
              </div>
          </div>
      </div>`
})
export class ExamResultsComponent implements OnInit {
  stats: Array<any>;
  resultId: number;

  constructor(private examService: ExamService) { }

  ngOnInit() {
      this.examService.resultId.subscribe(value => {
          this.resultId = value;
      },
      error => console.error(error));

      this.examService.getExamStat(this.resultId).subscribe(data => {
          this.stats = data;
      },
      error => console.error(error));
  }

}
