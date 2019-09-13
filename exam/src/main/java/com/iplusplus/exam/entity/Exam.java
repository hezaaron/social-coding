package com.iplusplus.exam.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @Table(name="EXAM")
@Getter @ToString @EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(force = true)
public final class Exam {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
    private final String name;
    private final String description;
    
    public Exam(String name, String description) {
    	this.name = name;
    	this.description = description;
    }
}