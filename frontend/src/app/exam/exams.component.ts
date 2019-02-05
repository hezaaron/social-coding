import { Component } from '@angular/core';
import { Exam } from "src/app/exam/model/exam";

@Component({
  selector: 'app-exams',
  template: `<app-exam-list [exams]=exams></app-exam-list>`
})
export class ExamsComponent {
  exams: Exam[] = [];
  
  constructor() { }
  
}
