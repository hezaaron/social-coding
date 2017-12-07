package org.onlinetest.dao;

import java.util.Hashtable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.onlinetest.entity.TestExam;
import org.onlinetest.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class TestExamDaoImpl extends AbstractDao<Integer, TestExam> implements TestExamDao {

	private static final Logger logger = LogManager.getLogger(TestExamDaoImpl.class);
	
	@Override
	public TestExam findExamByName(String name) {
		logger.info("ExamId : {}", name);
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("name", name));
		return (TestExam)criteria.uniqueResult();
	}
	
	private TestExam findExam(int id) {
		logger.info("ExamId : {}", id);
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		return (TestExam)criteria.uniqueResult();
	}
	
	@Override
	public String getExamName(int examId) {
		TestExam exam = this.findExam(examId);
		logger.info("ExamName : {}", exam.getName());
		String hql = "select name from TestExam as t where t.id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", examId);
		String result = (String) query.uniqueResult();
		return result;
	}
	
	@Override
	public String getExamDescription(int examId) {
		TestExam exam = this.findExam(examId);
		logger.info("Fetching {} exam description", exam.getName());
		String hql = "select description from TestExam as t where t.id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", examId);
		String result = (String) query.uniqueResult();
		return result;
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
