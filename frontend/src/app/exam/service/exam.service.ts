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

	private examResultId = new BehaviorSubject<number>( 0 );
	resultId = this.examResultId.asObservable();

	constructor( private http: HttpClient ) { }

	getExams(): Observable<Array<Exam>> {
		return this.http.get<Exam[]>( baseUrl );
	}

	getExam( id: number ): Observable<any> {
		return this.http.get( `${baseUrl}/${id}` );
	}

	getQuestions( id: number ): Observable<Array<Question>> {
		return this.http.get<Array<Question>>( `${baseUrl}/questions/${id}` );
	}

	getAnswers( id: number ): Observable<Array<Option>> {
		return this.http.get<Array<Option>>( `${baseUrl}/answers/${id}` );
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
}
