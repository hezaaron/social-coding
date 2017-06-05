package org.onlinetest.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;

@Entity
@Table(name = "question")
public class Question implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "question_id", updatable = false, nullable = false)
	private Long id;
	
	@Version
	@Column(name = "version")
	private int version;

	@Column
	private String title;

	@Column(name="problem_description")
	private String problemDescription;

	@Column(name="multi_answer")
	private int multiAnswer;

	@Column
	private String answer;

	@Column
	private AnswerChoice[] choices;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProblemDescription() {
		return problemDescription;
	}

	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}

	public int getMultiAnswer() {
		return multiAnswer;
	}

	public void setMultiAnswer(int multiAnswer) {
		this.multiAnswer = multiAnswer;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public AnswerChoice[] getChoices() {
		return choices;
	}

	public void setChoices(AnswerChoice[] choices) {
		this.choices = choices;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (title != null && !title.trim().isEmpty())
			result += "title: " + title;
		if (problemDescription != null && !problemDescription.trim().isEmpty())
			result += ", problemDescription: " + problemDescription;
		result += ", multiAnswer: " + multiAnswer;
		if (answer != null && !answer.trim().isEmpty())
			result += ", answer: " + answer;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Question)) {
			return false;
		}
		Question other = (Question) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

}