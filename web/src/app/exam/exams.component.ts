import { Component } from '@angular/core';
import { Quiz } from './model/quiz';

@Component( {
	selector: 'app-exams',
	template: `<app-exam-list [quizzes]=quizzes></app-exam-list>`
} )
export class ExamsComponent {
	quizzes: Quiz[] = [];

	constructor() { }

}
