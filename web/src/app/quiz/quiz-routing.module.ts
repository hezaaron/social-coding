import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OktaAuthGuard } from '@okta/okta-angular';
import { QuizzesComponent } from './quizzes.component';
import { QuizComponent } from './quiz/quiz.component';
import { QuizResultComponent } from './quiz-result/quiz-result.component';
import { QuizInfoComponent } from '../quiz/quiz-info/quiz-info.component';

export function onAuthRequired( { oktaAuth, router } ) {
	router.navigate( ['/login'] );
}

const routes: Routes = [{
	path: 'quizzes', component: QuizzesComponent,
	canActivate: [OktaAuthGuard], data: { onAuthRequired }
}, {
	path: 'quizzes/:id', component: QuizInfoComponent,
	canActivate: [OktaAuthGuard], data: { onAuthRequired }
},{
	path: 'quiz/:id', component: QuizComponent,
	canActivate: [OktaAuthGuard], data: { onAuthRequired }
}, {
	path: 'resultstat/:id', component: QuizResultComponent,
	canActivate: [OktaAuthGuard], data: { onAuthRequired }
}]

@NgModule( {
	imports: [
		RouterModule.forChild( routes )
	],
	exports: [
		RouterModule
	]
} )
export class QuizRoutingModule { }
