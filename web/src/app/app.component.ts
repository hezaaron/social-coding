import { Component } from '@angular/core';
import { OktaAuthService } from '@okta/okta-angular';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'iPlusplus';
  isAuthenticated: boolean;  
  
  constructor( private oktaAuthService: OktaAuthService ) {
    this.oktaAuthService.$authenticationState.subscribe(( isAuthenticated: boolean ) => this.isAuthenticated = isAuthenticated );
  }
  
  async ngOnInit() {
    this.isAuthenticated = await this.oktaAuthService.isAuthenticated();
  }
  
  async logout() {
    await this.oktaAuthService.logout('/');
  }
}