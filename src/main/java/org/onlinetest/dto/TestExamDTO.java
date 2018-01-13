package org.onlinetest.dto;

import java.io.Serializable;
import java.util.List;

public class TestExamDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer examId;
	private List<Integer> answers;

	public TestExamDTO() {}
	
	public TestExamDTO(Integer id, Integer examId) {
		this.id = id;
		this.examId = examId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public List<Integer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Integer> answers) {
		this.answers = answers;
	}
	
}