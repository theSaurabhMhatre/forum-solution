package com.forum.app.util;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.question.service.QuestionEntity;

public class SortUtility {

	public static Set<QuestionEntity> sortQuestions(List<QuestionEntity> questions) {
		Set<QuestionEntity> sortedQuestions = new TreeSet<QuestionEntity>(
				new Comparator<QuestionEntity>() {
					public int compare(QuestionEntity quesOne,
							QuestionEntity quesTwo) {
						Long one = quesOne.getLikes();
						Long two = quesTwo.getLikes();
						if (one > two) {
							return -1;
						} else {
							return 1;
						}
					}
				});
		for (QuestionEntity ques : questions) {
			sortedQuestions.add(ques);
		}
		return sortedQuestions;
	}	
	
	public static Set<AnswerEntity> sortAnswers(List<AnswerEntity> answers) {
		Set<AnswerEntity> sortedAnswers = new TreeSet<AnswerEntity>(
				new Comparator<AnswerEntity>() {
					public int compare(AnswerEntity ansOne, AnswerEntity ansTwo) {
						Long one = ansOne.getLikes();
						Long two = ansTwo.getLikes();
						if (one > two) {
							return -1;
						} else {
							return 1;
						}
					}
				});
		for (AnswerEntity answer : answers) {
			sortedAnswers.add(answer);
		}
		return sortedAnswers;
	}
	
}
