import { Component, OnInit } from '@angular/core';
import { ExamService } from './shared/exam/exam.service';

@Component({
    selector: 'app-result',
    template: `<div class="container">
                    <h3 class="text-sm-center">{{examResult}}</h3>
                    <div class="mt-4">
                        <div *ngIf="!resultStat">Loading...</div>
                        <table *ngIf="resultStat" class="table">
                            <tr><td>Start:</td><td>{{resultStat[2]}}</td></tr> 
                            <tr><td>Finish:</td><td>{{resultStat[3]}}</td></tr>
                            <tr><td class="text-nowrap">Number of Questions:</td><td>{{resultStat[0]}}</td></tr>
                            <tr><td>Score:</td><td [ngClass]="{'text-success': pass, 'text-danger': !pass}">{{resultStat[1]}}</td></tr>
                            <tr><td>Max Score:</td><td>{{resultStat[5]}}</td></tr> 
                        </table>
                    </div>
                </div>`
})

export class ResultComponent implements OnInit {
    examResult: string;
    resultStat: Array<any>;
    resultId: number;
    pass: boolean;
    
    constructor(private examService: ExamService) {}
    
    ngOnInit() {
        this.examService.resultId.subscribe(value => {
            this.resultId = value;
        },
        error => console.error(error));

        this.examService.getStat(this.resultId).subscribe(data => {
            this.resultStat = data;
            this.examResult = this.resultStat[4];
            this.pass = this.resultStat[1] >= 50;
        },
        error => console.error(error))
    }
}