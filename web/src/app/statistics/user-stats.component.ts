import { Component } from '@angular/core';
import { UserStats } from './user-stats.model';
import { UserStatsService } from './user-stats.service';
import { OktaAuthService } from '@okta/okta-angular';

@Component( {
    selector: 'app-userstats',
    template: `
        <div class="container">
        <h6 class="text-sm-center text-info mt-5">Your Stats</h6>
          <ng-container *ngIf="userstats; else loading">
          <table class="table">
            <tbody>
            <tr>
              <td class="info">User</td>
              <td>{{userstats.username}}</td>
            </tr>
            <tr>
              <td class="info">Score</td>
              <td>{{userstats.score}}</td>
            </tr>
            <tr>
              <td class="info">Badges</td>
              <td>{{userstats.badges}}</td>
            </tr>
            </tbody>
          </table>
          </ng-container>
          <ng-template #loading>Loading your stats...</ng-template>
      </div>
    `
} )
export class UserStatsComponent {
  userstats: UserStats;
  username: string;
  isAuthenticated: boolean;

    constructor( private userstatsService: UserStatsService, private oktaService: OktaAuthService ) {
        this.oktaService.$authenticationState.subscribe(( isAuthenticated: boolean ) => this.isAuthenticated = isAuthenticated );
    }

    async ngOnInit() {
        this.isAuthenticated = await this.oktaService.isAuthenticated();
    
        if ( this.isAuthenticated ) {
            const userClaims = await this.oktaService.getUser();
            this.username = userClaims.name;
        }
        
        console.log(this.username);
        
        this.userstatsService.getUserStats(this.username).subscribe(userstats => {
            this.userstats = userstats;
        });
    }
}