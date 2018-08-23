package com.iplusplus.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity @Table(name="QUESTION")
@Getter @Setter
@EqualsAndHashCode @ToString
public final class Question extends AbstractPersistable<Integer> {

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXAM_ID")
    private Exam exam;
	
    @Column
    private String name;
    
    @Column
    private String code;
    
    @Column(name = "MULTI_ANSWER", nullable = false)
    private boolean multiAnswer = false;
	
}
