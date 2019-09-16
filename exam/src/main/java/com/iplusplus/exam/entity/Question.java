package com.iplusplus.exam.entity;

import java.io.Serializable;

import javax.persistence.Column;
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
@ToString(exclude={"exam"})
@EqualsAndHashCode(exclude= {"exam"})
@RequiredArgsConstructor
public final class Question implements Serializable {

	private static final long serialVersionUID = 6292560590697622204L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "exam_id")
    private final Exam exam;
    private final String name;
    private final String code;
    @Column(nullable = false)
    private boolean multiAnswer = false;
	
    public Question() {
    	this(null, null, null);
    }
}