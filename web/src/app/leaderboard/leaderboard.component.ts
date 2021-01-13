import { Component } from '@angular/core';
import { Leader } from './leader.model';
import { LeaderBoardService } from './leaderboard.service';

@Component( {
    selector: 'app-leaderboard',
    template: `
        <div class="container">
        <h6 class="text-sm-center text-info mt-3">Leaderboard</h6>
          <ng-container *ngIf="leaders; else loading">
          <table class="table">
            <thead>
              <tr>
                <th scope="col">User</th>
                <th scope="col">Score</th>
              </tr>
            </thead>
            <tbody *ngFor="let leader of leaders">
            <tr>
              <td>{{leader.username}}</td>
              <td>{{leader.totalScore}}</td>
            </tr>
            </tbody>
          </table>
          </ng-container>
          <ng-template #loading>Loading Leaders...</ng-template>
      </div>
    `
} )
export class LeaderBoardComponent {
  leaders: Leader[] = [];

    constructor( private leaderService: LeaderBoardService ) { }

    async ngOnInit() {
        this.leaderService.getLeaderBoard().subscribe(leaders => {
            this.leaders = leaders;
        });
    }
}