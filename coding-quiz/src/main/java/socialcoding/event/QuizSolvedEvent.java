package socialcoding.event;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public final class QuizSolvedEvent implements Serializable {

	private static final long serialVersionUID = 4419489739197516400L;
    private final String username;
    private final int score;
    
}