import { Component, OnInit } from '@angular/core';
import { ExamService } from './shared/exam/exam.service';

@Component({
    selector: 'app-result',
    template: `<div class="container">
                    <h5 class="text-sm-center">{{resultTitle}}</h5>
                    <div class="mt-4">
                        <div *ngIf="!stats">Loading...</div>
                        <table *ngIf="stats" class="table">
                            <tr><td>Start:</td><td>{{stats[1]}}</td></tr> 
                            <tr><td>Finish:</td><td>{{stats[2]}}</td></tr>
                            <tr><td class="text-nowrap">Number of Questions:</td><td>{{stats[3]}}</td></tr>
                            <tr><td>Score:</td><td [ngClass]="{'text-success': pass, 'text-danger': !pass}">{{stats[4]}}</td></tr>
                            <tr><td>Max Score:</td><td>{{stats[5]}}</td></tr> 
                        </table>
                    </div>
                </div>`
})

export class ResultComponent implements OnInit {
    resultTitle: string;
    stats: Array<any>;
    resultId: number;
    pass: boolean;
    
    constructor(private examService: ExamService) {}
    
    ngOnInit() {
        this.examService.resultId.subscribe(value => {
            this.resultId = value;
        },
        error => console.error(error));

        this.examService.getExamStat(this.resultId).subscribe(data => {
            this.stats = data;
            this.resultTitle = this.stats[0];
            this.pass = this.stats[4] >= 50;
        },
        error => console.error(error))
    }
}