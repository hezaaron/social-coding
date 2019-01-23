import { getTestBed, TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpParams } from '@angular/common/http';
import { ExamService } from './exam.service';

describe('ExamService', () => {
  let injector: TestBed;
  let service: ExamService;
  let httpMock: HttpTestingController;
  const data = [
        {id: '1', name: 'Beginner Java', Description: 'Java for beginners'},
        {id: '2', name: 'Advance Java', Description: 'Java for Pro'}
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ExamService]
    });
    
    injector = getTestBed();
    service = injector.get(ExamService);
    httpMock = injector.get(HttpTestingController);
  });
  
  afterEach(() => {
      httpMock.verify();
  });

  it('should be created', inject([ExamService], (service: ExamService) => {
    expect(service).toBeTruthy();
  }));
  
  it('should return an Observable<exams[]>', () => {
    service.getAll().subscribe(exams => {
      expect(exams.length).toBe(2);
      expect(exams[0].id).toBe('1');
      expect(exams[0].name).toBe('Beginner Java');
      expect(exams).toEqual(data);
    });
    
    const req = httpMock.expectOne(`${service.API}`);
    expect(req.request.method).toBe("GET");
    req.flush(data);
  });
});