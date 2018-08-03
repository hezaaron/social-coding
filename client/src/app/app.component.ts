import { Component } from '@angular/core';
import { OktaAuthService } from '@okta/okta-angular';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Iplusplus Test Exams'
  isAuthenticated: boolean;

  constructor(public oktaAuth: OktaAuthService, public router: Router) {
      this.oktaAuth.$authenticationState.subscribe((isAuthenticated: boolean) => this.isAuthenticated = isAuthenticated);
  }
  
  async ngOnInit(){
      this.isAuthenticated = await this.oktaAuth.isAuthenticated();
  }
  
  login() {
      this.oktaAuth.loginRedirect('/profile');
  }
  
  async logout() {
      await this.oktaAuth.logout();
      this.router.navigateByUrl('/login');
  }
}
