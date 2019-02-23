import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ExamListComponent } from './exam-list/exam-list.component';
import { ExamPaperComponent } from './exam-paper/exam-paper.component';
import { ExamService } from './service/exam.service';
import { ExamResultsComponent } from './exam-results/exam-results.component';
import { FormatTimePipe } from './pipes/format-time.pipe';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HighlightModule } from 'ngx-highlightjs';
import { ConfirmationPopoverModule } from 'angular-confirmation-popover';
import { ExamsComponent } from '../exam/exams.component';
import { ExamRoutingModule } from './exam-routing.module';


@NgModule( {
	imports: [
		CommonModule,
		FormsModule,
		ReactiveFormsModule,
		HighlightModule.forRoot(),
		ConfirmationPopoverModule.forRoot( { confirmButtonType: 'danger' } ),
		ExamRoutingModule
	],
	declarations: [
		ExamListComponent,
		ExamPaperComponent,
		FormatTimePipe,
		ExamResultsComponent,
		ExamsComponent
	],
	providers: [
		ExamService
	]
} )
export class ExamModule { }
