import { Observable, of } from 'rxjs';
import { Quiz } from '../../quiz/model/quiz';

export const quizServiceStub = {
	quizList: [{
		id: 1,
		name: 'JavaScript Quiz',
		description: 'JavaScript Beginner Test',
	}, {
		id: 2,
		name: 'Angular Quiz',
		description: 'Angular Beginner Test',
	}],

	getQuizzes(): Observable<Quiz[]> {
		return of( this.quizList );
	}
}