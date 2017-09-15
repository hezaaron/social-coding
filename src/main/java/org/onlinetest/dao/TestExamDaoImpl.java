package org.onlinetest.dao;

import java.util.Hashtable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.onlinetest.model.TestExam;
import org.onlinetest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TestExamDaoImpl implements TestExamDao {

	@Autowired
	private SessionFactory sessionFactory;
	private static final Logger log = LogManager.getLogger(TestExamDaoImpl.class);
	
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public TestExam[] loadNewExam(User user) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public void saveExamAnswer(User user, Hashtable<Integer, String> answer, int totalScore) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveExamScore(User user, int totalScore) {
		// TODO Auto-generated method stub

	}

}
