import { Component, OnInit } from '@angular/core';
import { ExamService } from '../shared/exam/exam.service';
import { Exam } from '../model/exam';

@Component({
  selector: 'app-exam-list',
  templateUrl: './exam-list.component.html',
  styleUrls: ['./exam-list.component.css']
})
export class ExamListComponent implements OnInit {
  exams: Array<Exam>;

  constructor(private examService: ExamService) { }
  
  trackById(index, exam) {
      return exam.id;
  }

  async ngOnInit() {
      this.examService.getExams().subscribe(data => {
          this.exams = data;
      },
      error => console.error(error));
  }

}
