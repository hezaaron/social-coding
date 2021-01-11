import { Component } from '@angular/core';
import { Quiz } from './model/quiz';

@Component( {
	selector: 'app-quizzes',
	template: `<app-quiz-list [quizzes]=quizzes></app-quiz-list>`
} )
export class QuizzesComponent {
  quizzes: Quiz[] = [];

	constructor() { }

}
