package socialcoding.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class QuizReport {

    private final String title;
    private final String startTime;
    private final String finishTime;
    private final int questionCount;
    private final int grade;
    private final int maxGrade;
}
