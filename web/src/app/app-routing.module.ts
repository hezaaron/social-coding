import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { OktaCallbackComponent } from '@okta/okta-angular';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

const appRoutes: Routes = [{
	path: 'login', component: LoginComponent
}, {
	path: 'implicit/callback', component: OktaCallbackComponent
}, {
	path: '', pathMatch: 'full', redirectTo: '/quizzes'
}, {
	path: 'quizzes', loadChildren: './quiz/quiz.module#QuizModule'
}, {
	path: '**', component: PageNotFoundComponent
}]

@NgModule( {
	imports: [
		RouterModule.forRoot( appRoutes ),
	],
	exports: [
		RouterModule
	]
} )
export class AppRoutingModule { }
