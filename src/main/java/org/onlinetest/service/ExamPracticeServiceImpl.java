package org.onlinetest.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.onlinetest.dao.ExamPracticeDao;
import org.onlinetest.entity.ExamPractice;
import org.onlinetest.entity.QuestionChoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("examPracticeService")
@Transactional
public class ExamPracticeServiceImpl implements ExamPracticeService {
	
	private static final double MAX_GRADE = 100;
	@Autowired
	private ExamPracticeDao examPracticeDao;
	@Autowired
	private TestExamService testExamService;

	@Override
	public ExamPractice saveExam(ExamPractice examPractice) {
		return examPracticeDao.saveExam(examPractice);
	}

	@Override
	public int saveAndGetId(ExamPractice examPractice) {
		return examPracticeDao.saveAndGetId(examPractice);
	}

	@Override
	public void update(ExamPractice examPractice) {
		examPracticeDao.updateExam(examPractice);
	}

	@Override
	public ExamPractice getExamPractice(int id) {
		return examPracticeDao.getExamPractice(id);
	}

	@Override
	public void calculateGrade(ExamPractice examPractice, int examId, List<Integer> userChoices) {
		if(examPractice == null || userChoices == null) {
			throw new IllegalArgumentException();
		}
		
		List<QuestionChoice> correctChoices = testExamService.getCorrectChoices(examId, true);
		
		double mark = (double) (MAX_GRADE / correctChoices.size());
		Set<Integer> correctCount = new HashSet<>();
		Set<Integer> incorrectCount = new HashSet<>();
		Set<Integer> answers = new HashSet<Integer>(userChoices);
		
		double grade = 0;
		for(QuestionChoice answer : correctChoices) {
			int id = answer.getId();
			if(answers.contains(id)) {
				grade += mark;
				correctCount.add(id);
			}else{
				incorrectCount.add(id);
			}
		}
		
		//fix for multiAnswers questions
		correctCount.removeAll(correctCount);
		
		examPractice.setCorrectAnswers(correctCount.size());
		examPractice.setGrade((int) Math.round(grade));
	}

}
