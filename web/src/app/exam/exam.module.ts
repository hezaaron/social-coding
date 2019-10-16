import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ExamListComponent } from './exam-list/exam-list.component';
import { ExamPaperComponent } from './exam-paper/exam-paper.component';
import { ExamService } from './service/exam.service';
import { ExamResultsComponent } from './exam-results/exam-results.component';
import { FormatTimePipe } from './pipes/format-time.pipe';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ExamsComponent } from '../exam/exams.component';
import { ExamRoutingModule } from './exam-routing.module';
import { ExamInfoComponent } from './exam-info/exam-info.component';
import { HighlightModule, HIGHLIGHT_OPTIONS } from 'ngx-highlightjs';
import { ConfirmationPopoverModule } from 'angular-confirmation-popover';

export function getHighlightLanguages() {
  return {
    typescript: () => import('highlight.js/lib/languages/typescript'),
    java: () => import('highlight.js/lib/languages/java')
  };
}

@NgModule( {
	imports: [
		CommonModule,
		FormsModule,
		ReactiveFormsModule,
		ExamRoutingModule,
		HighlightModule,
    ConfirmationPopoverModule.forRoot( { confirmButtonType: 'danger' } )
	],
	declarations: [
		ExamListComponent,
		ExamPaperComponent,
		FormatTimePipe,
		ExamResultsComponent,
		ExamsComponent,
		ExamInfoComponent
	],
	providers: [
		ExamService,
		{ provide: HIGHLIGHT_OPTIONS, useValue: { languages: getHighlightLanguages()}},
	]
} )
export class ExamModule { }
