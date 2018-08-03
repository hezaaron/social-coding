import { Component, OnInit } from '@angular/core';
import { Router, NavigationStart } from '@angular/router';
import { OktaAuthService } from '@okta/okta-angular';
import * as OktaSignIn from '@okta/okta-signin-widget';

@Component({
    selector: 'app-secure',
    template: `
        <div class="site-main">
            <h2>Ipusplus Test Exam</h2>
            <p>Iplusplus exams are multiple choice questions<br>that will help you prepare for your technical interview</p>
        </div>
        <div id="okta-signin-container"></div>`
})

export class LoginComponent {
    signIn;
    widget = new OktaSignIn({
        baseUrl: 'https://dev-193618.oktapreview.com'
    })
    
    constructor(oktaAuth: OktaAuthService, router: Router) {
        this.signIn = oktaAuth;
        
        router.events.forEach(event => {
            if(event instanceof NavigationStart) {
                switch(event.url) {
                  case '/login':
                    break;
                  case '/testexams':
                    break;
                  default:
                    this.widget.remove();
                    break;
                }
            }
        });
    }
    
    ngOnInit() {
        this.widget.renderEl({el: '#okta-signin-container'}, (res) => {
            if(res.status === 'SUCCESS'){
                this.signIn.loginRedirect('/', { sessionToken: res.session.token });
                this.widget.hide();
            }
        },
        (err) => {
            throw err;
        }
      );
    }
}