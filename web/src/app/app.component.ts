import { Component } from '@angular/core';
import { OktaAuthService, } from '@okta/okta-angular';
import { Subscription } from 'rxjs';
import { SharedDataService } from './coding-quiz/service/shared-data.service';
import { UserStats } from './gamification/statistics/user-stats.model';
import { UserStatsService } from './gamification/statistics/user-stats.service';
import { Leader } from './gamification/leaderboard/leader.model';
import { LeaderBoardService } from './gamification/leaderboard/leaderboard.service';

@Component( {
	selector: 'app-root',
	templateUrl: './app.component.html'
} )
export class AppComponent {
	logo = 'assets/logo.png';
	title = 'Social Coding';
	isAuthenticated: boolean;
	isNavbarCollapsed = true;
	username: string;
    userstats: UserStats;
    leaders: Leader[] = [];
	private subscription: Subscription;

	constructor( public oktaService: OktaAuthService, private sharedDataService: SharedDataService,
                 private statsService: UserStatsService, private leaderService: LeaderBoardService ) {
		this.oktaService.$authenticationState.subscribe(( isAuthenticated: boolean ) => this.isAuthenticated = isAuthenticated );
	}

	async ngOnInit() {
		this.isAuthenticated = await this.oktaService.isAuthenticated();
	    this.subscription = this.sharedDataService.username.subscribe( name => {
            this.username = name;
	    },
        error => console.error( error ) );
	}
    
    refresh() {
        this.leaderService.getLeaderBoard().subscribe( leaders => {
            this.leaders = leaders;
        })
        
        this.statsService.getUserStats(this.username).subscribe(userstats => {
            this.userstats = userstats;
        })
    }

	async logout() {
		await this.oktaService.logout( 'login' );
	}
	
	ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}