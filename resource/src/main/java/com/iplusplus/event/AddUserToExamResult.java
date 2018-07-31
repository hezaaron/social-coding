package com.iplusplus.event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.iplusplus.domain.ExamResult;

@Component
@RepositoryEventHandler(ExamResult.class)
public class AddUserToExamResult {

	private static final Logger logger = LogManager.getLogger(AddUserToExamResult.class);
	
	@HandleBeforeCreate
	public void handleCreate(ExamResult examResult) {
		final String username = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("Creating exam result for user {}", username);
		examResult.setUser(username);
	}
}
