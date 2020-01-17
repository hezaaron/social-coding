import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { QuizListComponent } from './quiz-list/quiz-list.component';
import { QuizComponent } from './quiz/quiz.component';
import { QuizService } from './service/quiz.service';
import { QuizResultComponent } from './quiz-result/quiz-result.component';
import { FormatTimePipe } from './pipes/format-time.pipe';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HighlightModule } from 'ngx-highlightjs';
import { ConfirmationPopoverModule } from 'angular-confirmation-popover';
import { QuizzesComponent } from '../quiz/quizzes.component';
import { QuizRoutingModule } from './quiz-routing.module';
import { QuizInfoComponent } from './quiz-info/quiz-info.component';


@NgModule( {
	imports: [
		CommonModule,
		FormsModule,
		ReactiveFormsModule,
		HighlightModule.forRoot(),
		ConfirmationPopoverModule.forRoot( { confirmButtonType: 'danger' } ),
		QuizRoutingModule
	],
	declarations: [
		QuizListComponent,
		QuizComponent,
		FormatTimePipe,
		QuizResultComponent,
		QuizzesComponent,
		QuizInfoComponent
	],
	providers: [
		QuizService
	]
} )
export class QuizModule { }
