import { QuizzesComponent } from './quizzes.component';
import { Quiz } from "src/app/quiz/model/quiz";

describe( 'QuizzesComponent Tests', () => {
	let quizzesComponent: QuizzesComponent = null;

	beforeEach(() => {
		quizzesComponent = new QuizzesComponent();
	} );

	it( 'should set instance correctly', () => {
		expect( quizzesComponent ).not.toBeNull();
	} );

	it( 'should be no quizzes if there is no data', () => {
		expect( quizzesComponent.quizzes.length ).toBe( 0 );
	} );

	it( 'should be quizzes if there is data', () => {
		const newQuiz: Quiz = {
			id: 1,
			name: 'Angular Test Quiz',
			description: 'Angular Beginner Test',
		};

		const quizList: Array<Quiz> = [newQuiz];
		quizzesComponent.quizzes = quizList;
		expect( quizzesComponent.quizzes.length ).toBe( 1 );
	} );

	afterEach(() => {
		quizzesComponent = null;
	} );

} );
