package com.iplusplus.exam.template;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import com.iplusplus.exam.dto.ExamResultDTO;
import com.iplusplus.exam.entity.Answer;
import com.iplusplus.exam.entity.Exam;
import com.iplusplus.exam.entity.ExamResult;
import com.iplusplus.exam.entity.Question;
import com.iplusplus.exam.model.ExamTime;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class ExamTemplate implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(Exam.class).addTemplate("valid", new Rule() {{
			add("id", sequence(Integer.class));
			add("name", random("Beginner Java", "Intermediate Java", "Advance Java"));
			add("description", "${name} exam");
		}}).addTemplate("examId", new Rule() {{
			add("id", sequence(Integer.class));
		}});
		
		Fixture.of(Question.class).addTemplate("valid", new Rule() {{
			add("id", sequence(Integer.class));
			add("exam", one(Exam.class, "examId"));
			add("name", "What is the output of the following program");
			add("code", random("System.out.print('Hello World');", "System.out.print('Welcome Java');"));
			add("multiAnswer", random(true, false));
		}}).addTemplate("questionId", new Rule() {{
			add("id", sequence(Integer.class));
		}});
		
		Fixture.of(Answer.class).addTemplate("valid", new Rule() {{
			add("id", random(Integer.class, range(1, 100)));
			add("question", one(Question.class, "valid"));
			add("name", random("Hello World", "Welcome Java"));
			add("correct", true);
		}}).addTemplate("answerId", new Rule() {{
			add("id", random(Integer.class, range(1, 100)));
		}});
		
		Fixture.of(ExamResult.class).addTemplate("valid", new Rule() {{
			add("id", random(Integer.class, range(1, 100)));
			add("user", random("hez", "aaron"));
			add("exam", one(Exam.class, "examId"));
			add("startTime", examTime.getTime());
			add("finishTime", examTime.getTime().plusMinutes(2));
			add("questionCount", 5);
			add("correctAnswers", random(Integer.class, range(1, 5)));
			add("grade", random(20, 40, 60, 80, 100));
		}});
		
		Fixture.of(ExamResultDTO.class).addTemplate("valid", new Rule() {{
			add("id", random(Integer.class, range(1, 100)));
			add("examId", random(Integer.class, range(1, 100)));
			add("userName", random("hez", "aaron"));
		}});
		
	}
	
	ExamTime examTime = new ExamTime(Clock.fixed(Instant.parse("2018-12-06T10:30:30.00Z"), ZoneId.systemDefault()));
}
