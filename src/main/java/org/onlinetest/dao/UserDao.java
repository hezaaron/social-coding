package org.onlinetest.dao;

import org.onlinetest.entity.User;

public interface UserDao {

	User findByUserName(String userName);
	void save(User user);
}
