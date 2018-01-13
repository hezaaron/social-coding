package org.onlinetest.dao;

import org.onlinetest.entity.ExamPractice;
import org.springframework.stereotype.Repository;

@Repository
public class ExamPracticeDaoImpl extends AbstractDao<Integer, ExamPractice> implements ExamPracticeDao {
	
	public ExamPractice saveExam(ExamPractice examPractice) {
		persist(examPractice);
		getSession().flush();
		return examPractice;
	}

	@Override
	public int saveAndGetId(ExamPractice examPractice) {
		persist(examPractice);
		getSession().flush();
		return examPractice.getId();
	}

	@Override
	public ExamPractice updateExam(ExamPractice examPractice) {
		return saveExam(examPractice);
	}

	@Override
	public ExamPractice getExamPractice(int id) {
		return getByKey(id);
	}
	
}
