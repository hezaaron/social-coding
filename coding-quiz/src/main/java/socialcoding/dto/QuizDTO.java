package socialcoding.dto;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public final class QuizDTO {
	
	private final Long id;
    private final Integer quizId;
    @Setter private List<Long> answers;
    @Setter private String username;
    
    public QuizDTO() {
        this(null, null);
    }
}