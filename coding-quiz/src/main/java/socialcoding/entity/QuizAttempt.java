package socialcoding.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity @Data
@ToString(exclude={"quiz"})
@EqualsAndHashCode(exclude= {"quiz"})
@RequiredArgsConstructor
public final class QuizAttempt {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private final Long id;
	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "quiz_id")
    private final Quiz quiz;
	private final Integer questionCount;
    private final LocalDateTime startTime;
    private LocalDateTime finishTime;
    private Integer correctAnswers;
    private Integer grade;
    private String user;
    
    public QuizAttempt() {
    	this(null, null, null, null);
    }
}