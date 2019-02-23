import { ExamsComponent } from './exams.component';
import { Exam } from "src/app/exam/model/exam";

describe( 'ExamsComponent Tests', () => {
	let examsComponent: ExamsComponent = null;

	beforeEach(() => {
		examsComponent = new ExamsComponent();
	} );

	it( 'should set instance correctly', () => {
		expect( examsComponent ).not.toBeNull();
	} );

	it( 'should be no exams if there is no data', () => {
		expect( examsComponent.exams.length ).toBe( 0 );
	} );

	it( 'should be exams if there is data', () => {
		const newExam: Exam = {
			id: 1,
			name: 'Angular Test Exam',
			description: 'Angular Beginner test',
		};

		const examsList: Array<Exam> = [newExam];
		examsComponent.exams = examsList;
		expect( examsComponent.exams.length ).toBe( 1 );
	} );

	afterEach(() => {
		examsComponent = null;
	} );

} );
