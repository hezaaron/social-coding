import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Exam } from '../model/exam';
import { Option } from '../model/option';
import { Question } from '../model/question';

const examBaseUrl = `${environment.examApiUrl}`;
const resultBaseUrl = `${environment.resultApiUrl}`;

@Injectable( {
	providedIn: 'root'
} )
export class ExamService {
	private examIdSubject = new BehaviorSubject<number>(0);
	examId = this.examIdSubject.asObservable();
	private resultIdSubject = new BehaviorSubject<number>( 0 );
	resultId = this.resultIdSubject.asObservable();
	private userNameSubject = new BehaviorSubject<string>( "" );
  userName = this.userNameSubject.asObservable();

	constructor( private http: HttpClient ) { }

	getExams(): Observable<Exam[]> {
		return this.http.get<Exam[]>( examBaseUrl );
	}

	getExam( id: number ): Observable<any> {
		return this.http.get( `${examBaseUrl}/exam/${id}` );
	}

	getQuestions( id: number ): Observable<Question[]> {
		return this.http.get<Question[]>( `${examBaseUrl}/questions/${id}` );
	}

	getExamAnswers( id: number ): Observable<Option[]> {
		return this.http.get<Option[]>( `${examBaseUrl}/exam-answers/${id}` );
	}

	postAnswers( userAnswer: any ): Observable<any> {
		return this.http.post( `${resultBaseUrl}`, userAnswer );
	}

	getExamStat( id: number ): Observable<any> {
		return this.http.get( `${resultBaseUrl}/${id}` );
	}

	updateExamId( id: number ) {
    this.examIdSubject.next( id );
  }
	
	updateResultId( id: number ) {
		this.resultIdSubject.next( id );
	}
	
	updateUserName( userName: string ) {
		this.userNameSubject.next( userName );
	}
}
