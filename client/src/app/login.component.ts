import { Component, OnInit } from '@angular/core';
import { UserAuthService } from './shared/okta/user.service';
import { OktaAuthService } from '@okta/okta-angular';

@Component({
    selector: 'app-secure',
    template: `<div *ngIf="!isAuthenticated" id="okta-signin-container" >
                   <div class="intro">
                       <h5 class="text-sm-center">iplusplus Exam Maker</h5>
                       <p class="mt-2">iplusplus exams are multiple choice coding questions that will help you 
                          prepare for your java technical interview and make a good impression.</p>
                   </div>
               </div>`
})

export class LoginComponent {
    
    isAuthenticated: boolean;
    
    constructor(private oktaService: OktaAuthService, private userAuthService: UserAuthService) {
     // Subscribe to authentication state changes
        this.oktaService.$authenticationState.subscribe((isAuthenticated: boolean)  => this.isAuthenticated = isAuthenticated);
    }
    
    async ngOnInit() {
        this.isAuthenticated = await this.oktaService.isAuthenticated();
        this.userAuthService.showLogin();
    }
}