import { fakeAsync, ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';
import { Quiz } from "src/app/exam/model/quiz";
import { RouterTestingModule } from "@angular/router/testing";
import { ExamService } from "src/app/exam/service/exam.service";
import { ExamModule } from "src/app/exam/exam.module";
import { ExamListComponent } from "src/app/exam/exam-list/exam-list.component";
import { Router, RouterModule } from "@angular/router";
import { examServiceStub } from "src/app/shared/mock/mock-exam-service";

let component: ExamListComponent;
let fixture: ComponentFixture<ExamListComponent>;
let page: Page;

describe( 'ExamListComponent tests', () => {
	beforeEach( async(() => {
		TestBed.configureTestingModule( {
			imports: [ExamModule, RouterModule.forRoot( [] )],
			providers: [
				{ provide: ExamService, useValue: examServiceStub },
			],
		} )
			.compileComponents()
			.then( createComponent );
	} ) );

	it( 'should display exams', () => {
		expect( page.examNameRows.length ).toBeGreaterThan( 0 );
		expect( page.examDescRows.length ).toBeGreaterThan( 0 );
	} );

	it( 'first exam should match first test exam', () => {
		const expectedExam = examServiceStub.examsList[0];
		const actualExamName = page.examNameRows[0].textContent;
		const actualExamDesc = page.examDescRows[0].textContent;
		expect( actualExamName ).toContain( expectedExam.name, 'JavaScript Test Exam' );
		expect( actualExamDesc ).toContain( expectedExam.description, 'JavaScript Beginner Test' );
	} );

	it( 'should render route as /:id', fakeAsync(() => {
		const link = fixture.debugElement.query( By.css( 'a' ) ).nativeElement.getAttribute( 'href' );
		expect( link ).toEqual( '/1' );
	} ) );

} );


function createComponent() {
	fixture = TestBed.createComponent( ExamListComponent );
	component = fixture.componentInstance;
	fixture.detectChanges();

	return fixture.whenStable().then(() => {
		fixture.detectChanges();
		page = new Page();
	} );
}

class Page {
	examNameRows: HTMLHeadingElement[];
	examDescRows: HTMLParagraphElement[];

	constructor() {
		const examNameNodes = fixture.nativeElement.querySelectorAll( 'h6' );
		const examDescNodes = fixture.nativeElement.querySelectorAll( 'p' );
		this.examNameRows = Array.from( examNameNodes );
		this.examDescRows = Array.from( examDescNodes );
	}
}
