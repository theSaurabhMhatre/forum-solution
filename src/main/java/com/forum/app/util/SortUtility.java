package com.forum.app.util;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.question.service.QuestionEntity;

/**
 * Utility class used for sorting questions and answers according to the number
 * of likes in descending order.
 * 
 * @author Saurabh Mhatre
 *
 */
public class SortUtility {

	/**
	 * Sorts questions according to the number of likes each has, also eliminates
	 * duplicate questions if any.
	 * 
	 * @param List<QuestionEntity> questions
	 * @return Set<QuestionEntity> sortedQuestions
	 */
	public static Set<QuestionEntity> sortQuestions(List<QuestionEntity> questions) {
		Set<QuestionEntity> sortedQuestions = new TreeSet<QuestionEntity>(new Comparator<QuestionEntity>() {
			public int compare(QuestionEntity quesOne, QuestionEntity quesTwo) {
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

	/**
	 * Sorts answers according to the number of likes each has, also eliminates
	 * duplicate answers if any.
	 * 
	 * @param List<AnswerEntity> answers
	 * @return Set<AnswerEntity> sortedAnswers
	 */
	public static Set<AnswerEntity> sortAnswers(List<AnswerEntity> answers) {
		Set<AnswerEntity> sortedAnswers = new TreeSet<AnswerEntity>(new Comparator<AnswerEntity>() {
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
