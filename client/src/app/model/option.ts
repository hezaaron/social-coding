export class Option {
    id: number;
    questionId: number;
    name: string;
    isAnswer: boolean;
    selected = false;

    constructor(data: any) {
        data = data || {};
        this.id = data.id;
        this.questionId = data.question;
        this.name = data.name;
        this.isAnswer = data.correct;
    }
}
