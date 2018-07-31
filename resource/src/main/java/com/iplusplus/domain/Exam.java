package com.iplusplus.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity @Table(name="EXAM")
@Getter @Setter
@EqualsAndHashCode @ToString
public final class Exam extends AbstractPersistable<Integer> implements Comparable<Exam>{

	@Column
    private String name;
	
    @Column
    private String description;

	@Override
	public int compareTo(Exam exam) {
		return name.compareTo(exam.getName());
	}
   
}
