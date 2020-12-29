package socialcoding.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity @Data
@RequiredArgsConstructor
public final class Quiz {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private final Integer id;
    private final String name;
    private final String description;
    
    public Quiz() {
    	this(null, null, null);
    }
}