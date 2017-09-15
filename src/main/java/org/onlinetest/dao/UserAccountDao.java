package org.onlinetest.dao;

import org.onlinetest.model.User;

public interface UserAccountDao {

	public User findByUserName(String userName);
}
