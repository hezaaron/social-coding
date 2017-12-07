package org.onlinetest.service;

import java.util.Hashtable;

import org.onlinetest.dao.TestExamDao;
import org.onlinetest.entity.TestExam;
import org.onlinetest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("testExamService")
@Transactional
public class TestExamServiceImpl implements TestExamService {

	@Autowired
	private TestExamDao testExamDao;
	
	@Override
	public TestExam findExamByName(String name) {
		return testExamDao.findExamByName(name);
	}
	
	@Override
	public String getExamName(int examId) {
		return testExamDao.getExamName(examId);
	}
	
	@Override
	public String getExamDescription(int id) {
		return testExamDao.getExamDescription(id);
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
