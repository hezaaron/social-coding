import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ExamListComponent } from "src/app/exam/exam-list/exam-list.component";


describe('ExamListComponent', () => {
  let component: ExamListComponent;
  let fixture: ComponentFixture<ExamListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExamListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExamListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
});
