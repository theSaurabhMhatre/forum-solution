package com.forum.mod.search.service;

import java.util.List;
import java.util.Map;

import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.question.service.QuestionEntity;

public class SearchEntity {
	private List<QuestionEntity> questions;
	
	private List<AnswerEntity> answers;
	
	private Map<String, Integer> counter;
	
	public List<QuestionEntity> getQuestions() {
		return questions;
	}
	
	public void setQuestions(List<QuestionEntity> questions) {
		this.questions = questions;
	}
	
	public List<AnswerEntity> getAnswers() {
		return answers;
	}
	
	public void setAnswers(List<AnswerEntity> answers) {
		this.answers = answers;
	}
	
	public Map<String, Integer> getCounter() {
		return counter;
	}
	
	public void setCounter(Map<String, Integer> counter) {
		this.counter = counter;
	}
	
}
