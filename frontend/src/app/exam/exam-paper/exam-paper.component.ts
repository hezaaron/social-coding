import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormBuilder } from '@angular/forms';
import { ExamService } from '../service/exam.service';
import { timer, Observable } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { Option } from '../model/option';
import { Question } from '../model/question';

@Component( {
	selector: 'app-exam',
	templateUrl: './exam-paper.component.html',
	styleUrls: ['./exam-paper.component.scss']
} )
export class ExamPaperComponent implements OnInit {
	cancelSubmit: boolean = false;
	confirmSubmit: boolean = false;
	countDown: Observable<number>;
	counter: number;
	examName: string;
	examId: number;
	options: Array<Option>;
	optionIndex: number;
	pager = { index: 0, size: 1, count: 1 };
	questions: Array<Question>
	filteredQuestion: Array<Question>;
	resultForm: FormGroup;
	userAnswers: number[] = [];

	constructor( private route: ActivatedRoute, private router: Router, private examService: ExamService, private formBuilder: FormBuilder ) {
		this.resultForm = this.formBuilder.group( {
			id: [''],
			examId: [''],
			answers: ['']
		} );
	}

	ngOnInit() {
		const id = +this.route.snapshot.paramMap.get( 'id' );
		if ( id ) {
			this.examId = id;
		} else {
			console.log( `Exam with id '${id}' not found, returning to list` );
			this.router.navigate( ['/'] );
		}

		this.startExam();
	}

	startExam(): void {
		this.examService.getExam( this.examId ).subscribe( exam => {
			this.examName = exam.name
			this.counter = exam.timer;
			this.resultForm.setValue( exam.result );
			this.startTimer();
		} );
		this.getQuestions();
	}

	getQuestions(): void {
		this.examService.getQuestions( this.examId ).subscribe( data => {
			this.questions = data;
			if ( this.questions ) this.pager.count = this.questions.length;
			this.filteredQuestion = this.questions.slice( this.pager.index, this.pager.size );
			this.getOptions();
		} );
	}

	getOptions(): void {
		if ( this.filteredQuestion ) {
			this.examService.getAnswers( this.filteredQuestion[0].id ).subscribe( data => {
				this.options = data;
			} );
		}
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

	nextQuestion( index: number ): void {
		if ( index >= 0 && index < this.pager.count ) {
			this.pager.index = index;
			this.pager.size++;
			this.filteredQuestion = this.questions.slice( this.pager.index, this.pager.size );
			this.getOptions();
			this.userAnswer();
		}
	}

	optionClicked(): void {
		this.options[this.optionIndex].selected = true;
	}

	userAnswer(): void {
		let answer = 0;
		this.options.forEach(( x ) => { if ( x.selected ) answer = x.id; } );
		this.userAnswers.push( answer );
		this.optionIndex = null;
	}

	submit(): void {
		if ( this.confirmSubmit ) {
			this.userAnswer();
			this.resultForm.controls['answers'].setValue( this.userAnswers );
			this.examService.postAnswers( this.resultForm.value ).subscribe( response => {
				this.examService.updateResultId( response.id )
				this.viewResult( response.id );
			} );
		}
	}

	private viewResult( id: number ): void {
		this.router.navigate( ['/resultstat', id] );
	}

}
