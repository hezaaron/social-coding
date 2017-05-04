package org.onlinetest.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;

@Entity
public class QuestionChoiceAnswer implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Version
	@Column(name = "version")
	private int version;

	@Column
	private int questionId;

	@Column
	private String choiceAnswer;

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

	
	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getChoiceAnswer() {
		return choiceAnswer;
	}

	public void setChoiceAnswer(String choiceAnswer) {
		this.choiceAnswer = choiceAnswer;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		result += "questionId: " + questionId;
		if (choiceAnswer != null && !choiceAnswer.trim().isEmpty())
			result += ", choiceAnswer: " + choiceAnswer;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof QuestionChoiceAnswer)) {
			return false;
		}
		QuestionChoiceAnswer other = (QuestionChoiceAnswer) obj;
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