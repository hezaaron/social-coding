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
@ToString(exclude={"question"})
@EqualsAndHashCode(exclude= {"question"})
@RequiredArgsConstructor
public final class Answer implements Serializable{

	private static final long serialVersionUID = -3457593334233409268L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "question_id")
    private final Question question;
    private final String name;
    @Column(name = "is_correct", nullable = false)
    private boolean correct = false;

    public Answer() {
    	this(null, null);
    }
}
