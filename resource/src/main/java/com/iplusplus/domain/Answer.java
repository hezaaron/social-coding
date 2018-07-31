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

@Entity @Table(name="ANSWER")
@Getter @Setter
@EqualsAndHashCode @ToString
public final class Answer extends AbstractPersistable<Integer> {

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_ID")
    private Question question;
	
    @Column
    private String name;

    @Column(name = "IS_CORRECT", nullable = false)
    private boolean correct = false;
}
