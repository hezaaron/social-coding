import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable({
  providedIn: 'root'
})
export class ExamService {
  public API = '//localhost:8080/testexams';
  public EXAM_API = this.API + '/exam';
    
  constructor(private http: HttpClient) { }
  
  getAll() : Observable<any> {
      return this.http.get(this.API);
  }
  
  get(id: string) : Observable<any> {
      return this.http.get(this.API + '/' + id);
  }
  
  getQuestions(id: string) : Observable<any> {
      return this.http.get(this.API + '/questions/' + id);
  }
  
  getAnswers(id: number) : Observable<any> {
      return this.http.get(this.API + '/answers/' + id);
  }
}
