import { Component } from '@angular/core';
import { OktaAuthService, } from './shared/okta/okta.service';

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

  constructor(private oktaService: OktaAuthService) {}
  
  async ngOnInit(){
      this.isAuthenticated = await this.oktaService.isAuthenticated();
      this.oktaService.user$.subscribe((isAuthenticated: boolean) => this.isAuthenticated = isAuthenticated);
      if (this.isAuthenticated) {
          this.user = this.oktaService.idTokenAsUser;
      }
      this.oktaService.user$.subscribe(user => {
          this.user = user;
      });
  }
  
  async logout() {
      await this.oktaService.logout();
  }
}
