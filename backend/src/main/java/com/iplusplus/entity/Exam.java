package com.iplusplus.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity @Table(name="EXAM")
@Data @EqualsAndHashCode(callSuper=false)
public final class Exam implements Comparable<Exam>{

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
    private String name;
    private String description;

	@Override
	public int compareTo(Exam exam) {
		return name.compareTo(exam.getName());
	}
   
}