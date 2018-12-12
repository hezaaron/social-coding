import { Component } from '@angular/core';
import { OktaAuthService, } from '@okta/okta-angular';
import { UserAuthService } from './shared/okta/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'iplusplus Test Exams'
  isAuthenticated: boolean;
  isNavbarCollapsed = true;
  user: any;

  constructor(private oktaService: OktaAuthService, private userAuthService: UserAuthService) {
   // Subscribe to authentication state changes
      this.oktaService.$authenticationState.subscribe((isAuthenticated: boolean)  => this.isAuthenticated = isAuthenticated);
  }
  
  async ngOnInit(){
      this.isAuthenticated = await this.oktaService.isAuthenticated();
      if(this.isAuthenticated) {
          this.user = this.userAuthService.tokenAsUser;
      }
  }
  
  async logout() {
      await this.oktaService.logout('login');
  }
}