package org.onlinetest.dao;

import java.util.List;

import org.onlinetest.entity.UserProfile;

public interface UserProfileDao {

	UserProfile findById(int id);
	UserProfile findByType(String type);
	List<UserProfile> findAll();
}
