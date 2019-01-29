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

@Entity @Table(name="ANSWER")
@Data @EqualsAndHashCode(callSuper=false)
public final class Answer {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "QUESTION_ID")
    private Question question;
    private String name;
    @Column(name = "IS_CORRECT", nullable = false)
    private boolean correct = false;

}