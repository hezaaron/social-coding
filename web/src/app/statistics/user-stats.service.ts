import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { UserStats } from './user-stats.model';

const baseUrl = `${environment.userStatsApiUrl}`;

@Injectable()
export class UserStatsService {
    
    constructor( private http: HttpClient ) {}
    
    getUserStats(name: string): Observable<UserStats> {
        return this.http.get<UserStats>( baseUrl + '?username=' + name );
    }
}