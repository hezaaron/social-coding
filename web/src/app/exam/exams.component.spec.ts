import { ExamsComponent } from './exams.component';
import { Quiz } from "src/app/exam/model/quiz";

describe( 'ExamsComponent Tests', () => {
	let examsComponent: ExamsComponent = null;

	beforeEach(() => {
		examsComponent = new ExamsComponent();
	} );

	it( 'should set instance correctly', () => {
		expect( examsComponent ).not.toBeNull();
	} );

	it( 'should be no exams if there is no data', () => {
		expect( examsComponent.quizzes.length ).toBe( 0 );
	} );

	it( 'should be exams if there is data', () => {
		const newExam: Quiz = {
			id: 1,
			name: 'Angular Test Exam',
			description: 'Angular Beginner test',
		};

		const examsList: Array<Quiz> = [newExam];
		examsComponent.quizzes = examsList;
		expect( examsComponent.quizzes.length ).toBe( 1 );
	} );

	afterEach(() => {
		examsComponent = null;
	} );

} );
