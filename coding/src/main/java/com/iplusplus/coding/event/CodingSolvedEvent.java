package com.iplusplus.coding.event;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CodingSolvedEvent implements Serializable {

	private static final long serialVersionUID = 4419489739197516400L;
	private final Long resultId;
    private final String userName;
    private final boolean solved;
}
