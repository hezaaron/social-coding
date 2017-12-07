package org.onlinetest.service;

import java.util.List;

import org.onlinetest.entity.UserProfile;

public interface UserProfileService {

	UserProfile findById(int id);
	UserProfile findByType(String type);
	List<UserProfile> findAll();
}
