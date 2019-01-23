package com.iplusplus.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity @Table(name="EXAM_RESULT")
@Data @EqualsAndHashCode(callSuper=false)
public final class ExamResult {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@JsonIgnore
    private String user;
	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "EXAM_ID")
    private Exam exam;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    @Column(name = "QUESTION_COUNT")
    private Integer questionCount;
    @Column(name = "CORRECT_ANSWERS")
    private Integer correctAnswers;
    private Integer grade;

}
