package org.onlinetest.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.onlinetest.entity.UserProfile;
import org.springframework.stereotype.Repository;

@Repository("userProfileDao")
public class userProfileDaoImpl extends AbstractDao<Integer, UserProfile> implements UserProfileDao {

	@Override
	public UserProfile findById(int id) {
		return getByKey(id);
	}
	
	@Override
	public UserProfile findByType(String type) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("type", type));
		return (UserProfile) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserProfile> findAll() {
		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("type"));
		return (List<UserProfile>) criteria.list();
	}

}
