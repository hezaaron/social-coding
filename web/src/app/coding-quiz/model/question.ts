export class Question {
	
    constructor(
        public id?: number,
        public examId?: number,
        public name?: string,
        public code?: string,
        public multiAnswer?: boolean) {}
}
