package com.iplusplus.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter @ToString
@EqualsAndHashCode
public class ExamTakenEvent {

	private final Integer examResultId;
    private final String userName;
    private final boolean passed;
}
