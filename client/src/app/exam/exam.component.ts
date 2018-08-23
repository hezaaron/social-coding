import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Subscription } from 'rxjs/Subscription';
import { ExamService } from '../shared/exam/exam.service';

@Component({
  selector: 'app-exam',
  templateUrl: './exam.component.html',
  styleUrls: ['./exam.component.css']
})
export class ExamComponent implements OnInit, OnDestroy {
  sub: Subscription;
  examId: string;
  examProperties: Array<any>;
  welcomeMsg: string;
  countDownTimer: number;
  questions: Array<any>;
  questionName: string;
  questionCode: string;
  questionOptions: Array<any>;
  pager = {index: 0, size: 1, count: 1};
  userAnswers: Array<any>;
  result: any;

  constructor(private route: ActivatedRoute, private router: Router, private examService: ExamService) { 
  }
  
  getExamProperties() {
      this.sub = this.route.params.subscribe(params => {
          const id = params['id'];
          if(id) {
              this.examId = id;
              this.examService.get(id).subscribe(data => {
                  this.examProperties = data;
                  this.welcomeMsg = this.examProperties[0];
                  this.countDownTimer = this.examProperties[1];
                  this.result = this.examProperties[2];
              },
              error => console.error(error));
          }else {
              console.log(`Exam with id '${id}' not found, returning to list`);
              this.router.navigate(['/testexams']);
          }
      })
  }

  ngOnInit() {
      this.getExamProperties();
      this.examQuestion();
      this.startTimer();
  }
  
  examQuestion() {
      this.examService.getQuestions(this.examId).subscribe(data => {
          this.questions = data;
          this.pager.count = this.questions.length;
          const question = this.questions.slice(this.pager.index, this.pager.size);
          this.questionName = question[0].name;
          this.questionCode = question[0].code;
          this.examService.getAnswers(question[0].id).subscribe(data => {
              this.questionOptions = data;
          },
          error => console.error(error));
      },
      error => console.error(error));
  }

  nextQuestion(index: number) {
      if(index >= 0 && index < this.pager.count) {
          this.pager.index = index;
          this.pager.size++;
          const nextQuestion = this.questions.slice(this.pager.index, this.pager.size);
          this.questionName = nextQuestion[0].name;
          this.questionCode = nextQuestion[0].code;
          this.examService.getAnswers(nextQuestion[0].id).subscribe(data => {
              this.questionOptions = data;
          },
          error => console.error(error));
      }
  }

  interval: number;
  displayTimer : string;
  startTimer() {
      let minute = "0" + ((this.countDownTimer / 60) >> 0);
      let second = "0" + (this.countDownTimer % 60);
      
      setInterval(() => {
          if(this.countDownTimer > 0) {
              this.countDownTimer--;
          }
          if(this.countDownTimer <= 0) {
              clearInterval(this.interval);
          }
         this.displayTimer = minute + ":" +second;
          
      },1000);
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
