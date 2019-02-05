import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ExamListComponent } from "src/app/exam/exam-list/exam-list.component";
import { ExamPaperComponent } from "src/app/exam/exam-paper/exam-paper.component";
import { ExamService } from "src/app/exam/service/exam.service";
import { ExamResultsComponent } from './exam-results/exam-results.component';
import { FormatTimePipe } from './pipes/format-time.pipe';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HighlightModule } from 'ngx-highlightjs';
import { ConfirmationPopoverModule } from "angular-confirmation-popover";
import { HTTP_INTERCEPTORS } from "@angular/common/http";
import { AuthInterceptor } from "../login/auth.interceptor";
import { RouterModule } from "@angular/router";
import { ExamsComponent } from "../exam/exams.component";


@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    HighlightModule.forRoot(),
    ConfirmationPopoverModule.forRoot({confirmButtonType: 'danger'})
  ],
  declarations: [
    ExamListComponent,
    ExamPaperComponent,
    FormatTimePipe,
    ExamResultsComponent,
    ExamsComponent
  ],
  providers: [
    ExamService,
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ]
})
export class ExamModule { }
