import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormBuilder } from '@angular/forms';
import { QuizService } from '../service/quiz.service';
import { SharedDataService } from '../service/shared-data.service';
import { timer, Observable, Subscription } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { Option } from '../model/option';
import { Question } from '../model/question';

@Component( {
	selector: 'app-quiz',
	templateUrl: './quiz.component.html',
	styleUrls: ['./quiz.component.scss']
} )
export class QuizComponent implements OnInit {
  username: string;
	cancelSubmit: boolean = false;
	confirmSubmit: boolean = false;
	countDown: Observable<number>;
	counter: number;
	quizName: string;
	quizId: number;
	questions: Question[] = [];
	pager = { index: 0, size: 1, count: 1 };
	filteredQuestion: Question[] = [];
	options: Option[] = [];
	filteredOptions: Option[] = [];
	optionIndex: number;
	resultForm: FormGroup;
	userAnswers: number[] = [];
	private subscription: Subscription;

	constructor( private route: ActivatedRoute, private router: Router, private quizService: QuizService,
	             private formBuilder: FormBuilder, private sharedDataService: SharedDataService ) {
		this.resultForm = this.formBuilder.group( {
			id: [''],
			quizId: [''],
			answers: [''],
			username: ['']
		} );
	}

	ngOnInit() {
		this.subscription = this.sharedDataService.quizId.subscribe( id => {
			this.quizId = id;
		},
			error => console.error( error ) );
		
		this.subscription = this.sharedDataService.username.subscribe( name => {
      this.username = name;
    },
      error => console.error( error ) );
		
		this.startQuiz();
	}

	startQuiz(): void {
		this.quizService.getQuiz( this.quizId ).subscribe( quiz => {
			this.quizName = quiz.name
			this.counter = quiz.timer;
			this.resultForm.setValue( quiz.result );
			this.startTimer();
		} );
		this.setQuestions();
		this.loadOptions();
	}

	setQuestions(): void {
		this.quizService.getQuestions( this.quizId ).subscribe( questions => {
			this.questions = questions;
			if ( this.questions ) this.pager.count = this.questions.length;
			this.filteredQuestion = this.questions.slice( this.pager.index, this.pager.size );
		} );
	}

	loadOptions(): void {
		this.quizService.getAnswers( this.quizId ).subscribe( answers => {
			for ( let answer of answers ) {
				this.options.push( new Option( answer ) );
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
			this.resultForm.controls['username'].setValue( this.username );
			this.quizService.postAnswers( this.resultForm.value ).subscribe( response => {
				this.sharedDataService.updateResultId( response.id );
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
