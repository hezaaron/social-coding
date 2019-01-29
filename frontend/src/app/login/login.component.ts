import { Component, OnInit } from '@angular/core';
import { OktaAuthService } from "@okta/okta-angular/dist";
import * as OktaSignIn from '@okta/okta-signin-widget/dist/js/okta-sign-in-no-jquery';

@Component({
  selector: 'app-login',
  template: `
      <div *ngIf="!isAuthenticated" id="okta-signin-container" >
          <div class="intro">
              <h5 class="text-sm-center">iplusplus Test Exams</h5>
              <p class="mt-2">iplusplus exams are multiple choice coding questions that will help you 
                 prepare for your java technical interview and make a good impression.
              </p>
          </div>
      </div>`
})
export class LoginComponent implements OnInit {
  isAuthenticated: boolean;
  signIn = new OktaSignIn({
            baseUrl: 'https://dev-193618.oktapreview.com',
            clientId: '0oaj268wh6uRIKLy50h7',
            redirectUri: 'http://localhost:4200/implicit/callback',
            authParams: {
                scopes: ['openid', 'profile', 'email']
            }
        });
  
  constructor(private oktaService: OktaAuthService) { }

  async ngOnInit() {
      this.isAuthenticated = await this.oktaService.isAuthenticated();
      
      this.signIn.renderEl({el: '#okta-signin-container'}, (response) => {
          if(response.status === 'SUCCESS'){
              this.oktaService.loginRedirect('/', { sessionToken: response.sessionToken});
          }
      },
      (err) => {
          throw err;
      });
  }

}
