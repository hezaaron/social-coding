import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExamService {
  public API = 'http://localhost:8080/testexams';
  public EXAM_API = this.API + '/exam';
  private examResultId = new BehaviorSubject<number>(0);
  resultId = this.examResultId.asObservable();
    
  constructor(private http: HttpClient) { }
  
  updateResultId(id: number) {
      this.examResultId.next(id);
  }
  
  getAll() : Observable<any> {
      return this.http.get(this.API);
  }
  
  getExam(id: string) : Observable<any> {
      return this.http.get(this.API + '/' + id);
  }
  
  getQuestions(id: string) : Observable<any> {
      return this.http.get(this.API + '/questions/' + id);
  }
  
  getAnswers(id: number) : Observable<any> {
      return this.http.get(this.API + '/answers/' + id);
  }
  
  postAnswers(userAnswer: any) : Observable<any> {
      return this.http.post(this.API + '/save', userAnswer);
  }

  getExamStat(id: number) : Observable<any> {
      return this.http.get(this.API + '/result/' + id);
  }

}