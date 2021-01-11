export class Report {
    
    constructor(
        public title?: string,
        public startTime?: string,
        public finishTime?: string,
        public questionCount?: number,
        public grade?: number,
        public maxGrade?: number) {}
}