package com.iplusplus.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@SuppressWarnings("unused")
public final class EntryDTO {

	private Integer id;
    private String name;
    
    public EntryDTO(Integer id, String name) {
    	this.id = id;
    	this.name = name;
    }
    
}
