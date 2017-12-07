package org.onlinetest.service;

import org.onlinetest.dao.UserDao;
import org.onlinetest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User findByUserName(String userName) {
		User user = userDao.findByUserName(userName);
        return user;
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDao.save(user);
	}

	@Override
	public boolean isUserNameUnique(Integer id, String userName) {
		User user = findByUserName(userName);
        return ( user == null || ((id != null) && (user.getId() == id)));
	}
}
