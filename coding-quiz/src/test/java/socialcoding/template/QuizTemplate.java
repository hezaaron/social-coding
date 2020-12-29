package socialcoding.template;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import socialcoding.dto.QuizDTO;
import socialcoding.entity.Answer;
import socialcoding.entity.QuizAttempt;
import socialcoding.entity.Question;
import socialcoding.entity.Quiz;
import socialcoding.model.QuizTimer;

public class QuizTemplate implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(Quiz.class).addTemplate("valid", new Rule() {{
			add("id", sequence(Integer.class));
			add("name", random("Beginner Java", "Intermediate Java", "Advance Java"));
			add("description", "${name} quiz");
		}}).addTemplate("id", new Rule() {{
			add("id", sequence(Integer.class));
		}});
		
		Fixture.of(Question.class).addTemplate("valid", new Rule() {{
			add("id", sequence(Long.class));
			add("quiz", one(Quiz.class, "id"));
			add("name", "What is the output of the following program");
			add("code", random("System.out.print('Hello World');", "System.out.print('Welcome Java');"));
			add("multiAnswer", random(true, false));
		}}).addTemplate("questionId", new Rule() {{
			add("id", sequence(Long.class));
		}});
		
		Fixture.of(Answer.class).addTemplate("valid", new Rule() {{
			add("id", random(Long.class, range(1L, 100L)));
			add("question", one(Question.class, "valid"));
			add("name", random("Hello World", "Welcome Java"));
			add("correct", true);
		}}).addTemplate("answerId", new Rule() {{
			add("id", random(Long.class, range(1L, 100L)));
		}});
		
		Fixture.of(QuizAttempt.class).addTemplate("valid", new Rule() {{
			add("id", random(Long.class, range(1L, 100L)));
			add("user", random("hez", "aaron"));
			add("quiz", one(Quiz.class, "id"));
			add("startTime", quizTimer.getTime());
			add("finishTime", quizTimer.getTime().plusMinutes(2));
			add("questionCount", 5);
			add("correctAnswers", random(Integer.class, range(1, 5)));
			add("grade", random(20, 40, 60, 80, 100));
		}});
		
		Fixture.of(QuizDTO.class).addTemplate("valid", new Rule() {{
			add("id", random(Long.class, range(1L, 100L)));
			add("quizId", random(Integer.class, range(1, 100)));
			add("username", random("hez", "aaron"));
		}});
		
	}
	
	QuizTimer quizTimer = new QuizTimer(Clock.fixed(Instant.parse("2018-12-06T10:30:30.00Z"), ZoneId.systemDefault()));
}
