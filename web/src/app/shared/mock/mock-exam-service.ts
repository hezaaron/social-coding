import { Observable, of } from 'rxjs';
import { Exam } from '../../exam/model/exam';

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

	getExams(): Observable<Exam[]> {
		return of( this.examsList );
	}
}