package org.onlinetest.dao;

import java.util.Hashtable;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.onlinetest.entity.Question;
import org.onlinetest.entity.QuestionChoice;
import org.onlinetest.entity.TestExam;
import org.onlinetest.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class TestExamDaoImpl extends AbstractDao<Integer, TestExam> implements TestExamDao {

	private static final Logger logger = LogManager.getLogger(TestExamDaoImpl.class);
	
	@Override
	public TestExam findExamByName(String name) {
		logger.info("ExamName : {}", name);
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("name", name));
		return (TestExam)criteria.uniqueResult();
	}
	
	@Override
	public String getExamName(int examId) {
		TestExam exam = this.findExam(examId);
		logger.info("ExamName : {}", exam.getName());
		return exam.getName();
	}
	
	@Override
	public String getExamDescription(int examId) {
		TestExam exam = this.findExam(examId);
		logger.info("Fetching {} exam description", exam.getName());
		return exam.getDescription();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Question> loadExam(int examId) {
		TestExam exam = this.findExam(examId);
		logger.info("Fetching {} exam questions", exam.getName());
		String hql = "from Question q where q.examId = :examId order by q.id asc";
		Query query = getSession().createQuery(hql);
		query.setParameter("examId", examId);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionChoice> getQuestionChoices(int examId) {
		TestExam exam = this.findExam(examId);
		logger.info("Fetching {} exam question options", exam.getName());
		String hql = "from QuestionChoice qc where qc.examId = :examId order by qc.questionId asc";
		Query query = getSession().createQuery(hql);
		query.setParameter("examId", examId);
		return query.list();
	}

	@Override
	public void saveExamAnswer(User user, Hashtable<Integer, String> answer, int totalScore) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveExamScore(User user, int totalScore) {
		// TODO Auto-generated method stub

	}
	
	private TestExam findExam(int id) {
		logger.info("ExamId : {}", id);
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		return (TestExam)criteria.uniqueResult();
	}

}
