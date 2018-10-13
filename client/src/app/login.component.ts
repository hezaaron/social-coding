import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OktaAuthService } from './shared/okta/okta.service';
import { Observable } from 'rxjs';

@Component({
    selector: 'app-secure',
    template: `<div id="okta-signin-container" >
                   <div class="intro">
                       <h5 class="text-sm-center">iplusplus Exam Maker</h5>
                       <p class="mt-2">iplusplus exams are multiple choice coding questions that will help you 
                          prepare for your java technical interview and make a good impression.</p>
                   </div>
               </div>`
})

export class LoginComponent { 
    
    constructor(private oktaService: OktaAuthService, private router: Router) {}
    
    ngOnInit() {
        this.oktaService.showLogin();
        // user authentication listener
        this.oktaService.user$.subscribe(user => {
            this.router.navigate(['/']);
        });
        
    }
}