import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OktaAuthGuard } from '@okta/okta-angular';
import { ExamsComponent } from './exams.component';
import { ExamPaperComponent } from './exam-paper/exam-paper.component';
import { ExamResultsComponent } from './exam-results/exam-results.component';
import { ExamInfoComponent } from '../exam/exam-info/exam-info.component';

export function onAuthRequired( { oktaAuth, router } ) {
	router.navigate( ['/login'] );
}

const examRoutes: Routes = [{
	path: 'testexams', component: ExamsComponent,
	canActivate: [OktaAuthGuard], data: { onAuthRequired }
}, {
	path: 'testexams/:id', component: ExamInfoComponent,
	canActivate: [OktaAuthGuard], data: { onAuthRequired }
},{
	path: 'exam/:id', component: ExamPaperComponent,
	canActivate: [OktaAuthGuard], data: { onAuthRequired }
}, {
	path: 'resultstat/:id', component: ExamResultsComponent,
	canActivate: [OktaAuthGuard], data: { onAuthRequired }
}]

@NgModule( {
	imports: [
		RouterModule.forChild( examRoutes )
	],
	exports: [
		RouterModule
	]
} )
export class ExamRoutingModule { }
