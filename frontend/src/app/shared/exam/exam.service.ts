import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Exam } from '../../model/exam';
import { Observable, BehaviorSubject } from 'rxjs';

const baseUrl = `${environment.apiUrl}`;

@Injectable({
  providedIn: 'root'
})
export class ExamService {
  private examResultId = new BehaviorSubject<number>(0);
  resultId = this.examResultId.asObservable();
    
  constructor(private http: HttpClient) { }
  
  updateResultId(id: number) {
      this.examResultId.next(id);
  }
  
  getExams() : Observable<Exam[]> {
      return this.http.get<Exam[]>(baseUrl);
  }
  
  getExam(id: string) : Observable<any> {
      return this.http.get(baseUrl + '/' + id);
  }
  
  getQuestions(id: string) : Observable<any> {
      return this.http.get(baseUrl + '/questions/' + id);
  }
  
  getAnswers(id: number) : Observable<any> {
      return this.http.get(baseUrl + '/answers/' + id);
  }
  
  postAnswers(userAnswer: any) : Observable<any> {
      return this.http.post(baseUrl + '/save', userAnswer);
  }

  getExamStat(id: number) : Observable<any> {
      return this.http.get(baseUrl + '/result/' + id);
  }

}