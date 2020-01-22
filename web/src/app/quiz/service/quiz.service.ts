import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Quiz } from '../model/quiz';
import { Option } from '../model/option';
import { Question } from '../model/question';

const quizBaseUrl = `${environment.quizApiUrl}`;
const resultBaseUrl = `${environment.resultApiUrl}`;

@Injectable( {
	providedIn: 'root'
} )
export class QuizService {
	private quizIdSubject = new BehaviorSubject<number>(0);
	quizId = this.quizIdSubject.asObservable();
	private resultIdSubject = new BehaviorSubject<number>( 0 );
	resultId = this.resultIdSubject.asObservable();
	private userNameSubject = new BehaviorSubject<string>( "" );
  userName = this.userNameSubject.asObservable();

	constructor( private http: HttpClient ) { }

	getQuizzes(): Observable<Quiz[]> {
		return this.http.get<Quiz[]>( quizBaseUrl );
	}

	getQuiz( id: number ): Observable<any> {
		return this.http.get( `${quizBaseUrl}/details/${id}` );
	}

	getQuestions( id: number ): Observable<Question[]> {
		return this.http.get<Question[]>( `${quizBaseUrl}/questions/${id}` );
	}

	getAnswers( id: number ): Observable<Option[]> {
		return this.http.get<Option[]>( `${quizBaseUrl}/answers/${id}` );
	}

	postAnswers( userAnswer: any ): Observable<any> {
		return this.http.post( `${resultBaseUrl}`, userAnswer );
	}

	getQuizStat( id: number ): Observable<any> {
		return this.http.get( `${resultBaseUrl}/${id}` );
	}

	updateQuizId( id: number ) {
    this.quizIdSubject.next( id );
  }
	
	updateResultId( id: number ) {
		this.resultIdSubject.next( id );
	}
	
	updateUserName( userName: string ) {
		this.userNameSubject.next( userName );
	}
}
