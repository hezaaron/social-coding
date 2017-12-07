package org.onlinetest.service;

import org.onlinetest.entity.User;

public interface UserService {

	User findByUserName(String userName);
	void saveUser(User user);
	boolean isUserNameUnique(Integer id, String userName);
}
