import { Observable, of } from 'rxjs';
import { Quiz } from '../../exam/model/quiz';

export const examServiceStub = {
	examsList: [{
		id: 1,
		name: 'JavaScript Test Exam',
		description: 'JavaScript Beginner test',
	}, {
		id: 2,
		name: 'Angular Test Exam',
		description: 'Angular Beginner test',
	}],

	getExams(): Observable<Quiz[]> {
		return of( this.examsList );
	}
}
