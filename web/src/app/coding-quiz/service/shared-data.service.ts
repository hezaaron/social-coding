import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedDataService {
  
  private quizIdObs = new BehaviorSubject<number>( 0 );
  quizId = this.quizIdObs.asObservable();
  
  private resultIdObs = new BehaviorSubject<number>( 0 );
  resultId = this.resultIdObs.asObservable();
  
  private usernameObs = new BehaviorSubject<string>( "" );
  username = this.usernameObs.asObservable();

  constructor() { }
  
  updateQuizId( id: number ) {
    this.quizIdObs.next( id );
  }
  
  updateResultId( id: number ) {
    this.resultIdObs.next( id );
  }
  
  updateUsername( username: string ) {
    this.usernameObs.next( username );
  }
}
