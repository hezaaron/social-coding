import { Component, OnInit, OnDestroy, Pipe, PipeTransform } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Subscription, timer } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { ExamService } from '../shared/exam/exam.service';
import { Option } from '../model/option';

@Component({
  selector: 'app-exam',
  templateUrl: './exam.component.html',
  styleUrls: ['./exam.component.css']
})
export class ExamComponent implements OnInit, OnDestroy {
  sub: Subscription;
  examId: string;
  examName: string;
  counter: number;
  examQuestions: Array<any>;
  filteredQuestion: Array<any>;
  questionOptions: Array<Option>;
  pager = {index: 0, size: 1, count: 1};
  userAnswers: any[] = [];
  resultForm: FormGroup;
  optionIndex: number;
  countDown: any;
  confirmSubmit: boolean = false;
  cancelSubmit: boolean = false;

  constructor(private route: ActivatedRoute, private router: Router, private examService: ExamService, private formBuilder: FormBuilder) {
      this.resultForm = this.formBuilder.group({
          id: [''],
          examId: [''],
          answers: ['']
      });
  }

  ngOnInit() {
      this.sub = this.route.params.subscribe(params => {
          const id = params['id'];
          if(id) {
              this.examId = id;
          }else {
             console.log(`Exam with id '${id}' not found, returning to list`);
             this.router.navigate(['/testexams']);
          }
      })
      
      this.examProperties();
      this.examQuestion();
  }
  
  examProperties() {
      this.examService.get(this.examId).subscribe(data => {
          const properties = data;
          this.examName = properties[0];
          this.counter = properties[1];
          this.resultForm.setValue(properties[2]);
          this.startTimer();
      },
      error => console.error(error));
  }
  
  optionClicked() {
      this.questionOptions[this.optionIndex].selected = true;
  }
  
  getUserAnswer(){
      let answer = 0;
      this.questionOptions.forEach((x) => { if(x.selected) answer = x.id;});
      this.userAnswers.push(answer);
      this.optionIndex = null;
  }
  
  examQuestion() {
      this.examService.getQuestions(this.examId).subscribe(data => {
          this.examQuestions = data;
          this.pager.count = this.examQuestions.length;
          this.filteredQuestion = this.examQuestions.slice(this.pager.index, this.pager.size);
          this.loadQuestionOptions();
      },
      error => console.error(error));
  }
  
  loadQuestionOptions() {
      if(this.filteredQuestion) {
          this.examService.getAnswers(this.filteredQuestion[0].id).subscribe(data => {
              let option = data;
              this.questionOptions = [
                    new Option(option[0]),
                    new Option(option[1]),
                    new Option(option[2]),
                    new Option(option[3])
                    ];
          },
         error => console.error(error));
      }
  }

  nextQuestion(index: number) {
      if(index >= 0 && index < this.pager.count) {
          this.pager.index = index;
          this.pager.size++;
          this.filteredQuestion = this.examQuestions.slice(this.pager.index, this.pager.size);
          this.loadQuestionOptions();
          this.getUserAnswer();
      }
  }
  
  submit() {
      if(this.confirmSubmit) {
          this.getUserAnswer();
          this.resultForm.controls['answers'].setValue(this.userAnswers);
          this.examService.postAnswers(this.resultForm.value).subscribe(response => {
              this.examService.updateResultId(response.id)
              this.viewResult(response.id);
          },
          error => console.error(error));
      }
  }

  startTimer() {
      this.countDown = timer(0,1000).pipe(
          take(this.counter),
          map(tick => {
              if(this.counter - (tick % this.counter) === 1 && this.pager.index === (this.pager.count - 1)) {
                  this.confirmSubmit = true;
                  this.submit();
              }
              if(this.counter - (tick % this.counter) === 1) this.nextQuestion(this.pager.index + 1);
              return this.counter - (tick % this.counter);
          })
      );
  }
  
  private viewResult(id: number) {
      this.router.navigate(['/resultstat', id]);
  }
  
  ngOnDestroy() {
    this.sub.unsubscribe();
  }
  
}

@Pipe({
    name: 'formatTime'
 })
 export class FormatTimePipe implements PipeTransform {

    transform(value: number): string {
      const minutes: number = Math.floor(value / 60);
      return ('00' + minutes).slice(-2) + ':' + ('00' + Math.floor(value - minutes * 60)).slice(-2);
    }
    
 }
