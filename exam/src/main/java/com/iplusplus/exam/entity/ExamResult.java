package com.iplusplus.exam.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity @Data
@ToString(exclude={"exam"})
@EqualsAndHashCode(exclude= {"exam"})
public final class ExamResult implements Serializable {

	private static final long serialVersionUID = 4452128691658159963L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@JsonIgnore
    private String user;
	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "exam_id")
    private Exam exam;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private Integer questionCount;
    private Integer correctAnswers;
    private Integer grade;

}