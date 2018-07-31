package com.iplusplus.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity @Table(name="EXAM_RESULT")
@Getter @Setter
@EqualsAndHashCode @ToString
public final class ExamResult extends AbstractPersistable<Integer> {

	@JsonIgnore
    private String user;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXAM_ID")
    private Exam exam;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date finish;

    @Column(name = "QUESTION_COUNT")
    private Integer questionCount;

    @Column(name = "CORRECT_ANSWERS")
    private Integer correctAnswers;

    @Column
    private Integer grade;

}
