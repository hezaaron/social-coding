package org.onlinetest.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.onlinetest.entity.User;
import org.springframework.stereotype.Repository;

@Repository("UserDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {	
	
	private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
	
	public User findByUserName(String userName) {
		logger.info("UserName : {}", userName);
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("userName", userName));
		User user = (User)criteria.uniqueResult();
		if(user!=null) {
			Hibernate.initialize(user.getUserProfiles());
		}
		return user;
	}

	@Override
	public void save(User user) {
		persist(user);
	}
}
