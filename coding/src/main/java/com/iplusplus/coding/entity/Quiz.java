package com.iplusplus.coding.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity @Data
@RequiredArgsConstructor
public final class Quiz implements Serializable {

	private static final long serialVersionUID = 1778231174760090288L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
    private final String name;
    private final String description;
    
    public Quiz() {
    	this(null, null);
    }
}