package com.iplusplus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity @Table(name="QUESTION")
@Data @EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(force = true)
public final class Question {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "EXAM_ID")
    private final Exam exam;
    private final String name;
    private final String code;
    @Column(name = "MULTI_ANSWER", nullable = false)
    private boolean multiAnswer = false;
	
}