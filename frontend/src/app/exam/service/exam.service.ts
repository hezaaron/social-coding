import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Exam } from '../model/exam';
import { Option } from '../model/option';
import { Question } from '../model/question';

const baseUrl = `${environment.apiUrl}`;

@Injectable( {
	providedIn: 'root'
} )
export class ExamService {
	private examIdSub = new BehaviorSubject<number>(0);
	examId = this.examIdSub.asObservable();
	private examResultId = new BehaviorSubject<number>( 0 );
	resultId = this.examResultId.asObservable();

	constructor( private http: HttpClient ) { }

	getExams(): Observable<Exam[]> {
		return this.http.get<Exam[]>( baseUrl );
	}

	getExam( id: number ): Observable<any> {
		return this.http.get( `${baseUrl}/exam/${id}` );
	}

	getQuestions( id: number ): Observable<Question[]> {
		return this.http.get<Question[]>( `${baseUrl}/questions/${id}` );
	}

	getExamAnswers( id: number ): Observable<Option[]> {
		return this.http.get<Option[]>( `${baseUrl}/exam-answers/${id}` );
	}

	postAnswers( userAnswer: any ): Observable<any> {
		return this.http.post( `${baseUrl}/save`, userAnswer );
	}

	getExamStat( id: number ): Observable<any> {
		return this.http.get( `${baseUrl}/result/${id}` );
	}

	updateResultId( id: number ) {
		this.examResultId.next( id );
	}
	
	updateExamId( id: number ) {
		this.examIdSub.next( id );
	}
}
