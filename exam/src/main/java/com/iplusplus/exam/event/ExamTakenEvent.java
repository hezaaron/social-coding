package com.iplusplus.exam.event;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ExamTakenEvent implements Serializable {

	private static final long serialVersionUID = 4419489739197516400L;
	private final Long examResultId;
    private final String userName;
    private final boolean passed;
}
