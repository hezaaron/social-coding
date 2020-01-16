import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormBuilder } from '@angular/forms';
import { ExamService } from '../service/exam.service';
import { timer, Observable, Subscription } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { Option } from '../model/option';
import { Question } from '../model/question';

@Component( {
	selector: 'app-exam',
	templateUrl: './exam-paper.component.html',
	styleUrls: ['./exam-paper.component.scss']
} )
export class ExamPaperComponent implements OnInit {
  userName: string;
	cancelSubmit: boolean = false;
	confirmSubmit: boolean = false;
	countDown: Observable<number>;
	counter: number;
	examName: string;
	examId: number;
	questions: Question[] = [];
	pager = { index: 0, size: 1, count: 1 };
	filteredQuestion: Question[] = [];
	options: Option[] = [];
	filteredOptions: Option[] = [];
	optionIndex: number;
	resultForm: FormGroup;
	userAnswers: number[] = [];
	private subscription: Subscription;

	constructor( private route: ActivatedRoute, private router: Router, private examService: ExamService, private formBuilder: FormBuilder ) {
		this.resultForm = this.formBuilder.group( {
			id: [''],
			examId: [''],
			answers: [''],
			userName: ['']
		} );
	}

	ngOnInit() {
		this.subscription = this.examService.examId.subscribe( id => {
			this.examId = id;
		},
			error => console.error( error ) );
		
		this.subscription = this.examService.userName.subscribe( name => {
      this.userName = name;
    },
      error => console.error( error ) );
		
		this.startExam();
	}

	startExam(): void {
		this.examService.getExam( this.examId ).subscribe( exam => {
			this.examName = exam.name
			this.counter = exam.timer;
			this.resultForm.setValue( exam.result );
			this.startTimer();
		} );
		this.setQuestions();
		this.loadOptions();
	}

	setQuestions(): void {
		this.examService.getQuestions( this.examId ).subscribe( questions => {
			this.questions = questions;
			if ( this.questions ) this.pager.count = this.questions.length;
			this.filteredQuestion = this.questions.slice( this.pager.index, this.pager.size );
		} );
	}

	loadOptions(): void {
		this.examService.getAnswers( this.examId ).subscribe( examAnswers => {
			for ( let examAnswer of examAnswers ) {
				this.options.push( new Option( examAnswer ) );
			}
			this.setOptions( this.filteredQuestion[0].id );
		} );
	}

	setOptions( id: number ): void {
		this.filteredOptions = this.options.filter( option => option.questionId === id );
	}

	nextQuestion( index: number ): void {
		if ( index >= 0 && index < this.pager.count ) {
			this.userAnswer();
			this.pager.index = index;
			this.pager.size++;
			this.filteredQuestion = this.questions.slice( this.pager.index, this.pager.size );
			this.setOptions( this.filteredQuestion[0].id );
		}
	}

	userAnswer(): void {
		let answer = 0;
		this.filteredOptions.forEach(( x ) => { if ( x.selected ) answer = x.id; } );
		this.userAnswers.push( answer );
		this.optionIndex = null;
	}

	startTimer(): void {
		this.countDown = timer( 0, 1000 ).pipe(
			take( this.counter ),
			map( tick => {
				if ( this.counter - ( tick % this.counter ) === 1 && this.pager.index === ( this.pager.count - 1 ) ) {
					this.confirmSubmit = true;
					this.submit();
				}
				if ( this.counter - ( tick % this.counter ) === 1 ) this.nextQuestion( this.pager.index + 1 );
				return this.counter - ( tick % this.counter );
			} )
		);
	}

	submit(): void {
		if ( this.confirmSubmit ) {
			this.userAnswer();
			this.resultForm.controls['answers'].setValue( this.userAnswers );
			this.resultForm.controls['userName'].setValue( this.userName );
			this.examService.postAnswers( this.resultForm.value ).subscribe( response => {
				this.examService.updateResultId( response.id );
				this.viewResult( response.id );
			} );
		}
	}

	optionClicked(): void {
		this.filteredOptions[this.optionIndex].selected = true;
	}

	private viewResult( id: number ): void {
		this.router.navigate( ['/resultstat', id] );
	}

	ngOnDestroy() {
		this.subscription.unsubscribe();
	}
}
