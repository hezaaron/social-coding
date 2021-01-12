import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Leader } from './leader.model';

const baseUrl = `${environment.leadersApiUrl}`;

@Injectable()
export class LeaderBoardService {
    
    constructor( private http: HttpClient ) {}
    
    getLeaderBoard(): Observable<Leader[]> {
        return this.http.get<Leader[]>( baseUrl );
    }
}