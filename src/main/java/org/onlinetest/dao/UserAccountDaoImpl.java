package org.onlinetest.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.onlinetest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("UserAccountDao")
public class UserAccountDaoImpl implements UserAccountDao {

	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction transaction = null;
	
	
	@Override
	public User findByUserName(String userName) {
		session = sessionFactory.openSession();
		transaction = session.getTransaction();
		User user = (User)session.load(User.class, new String(userName));
		transaction.commit();
		return user;
	}

}
