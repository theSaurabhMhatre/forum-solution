package com.forum.mod.search.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forum.app.exception.ForumException;
import com.forum.app.util.ValidationUtility.Type;
import com.forum.mod.answer.factory.AnswerBusinessFactory;
import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.question.factory.QuestionBusinessFactory;
import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.search.service.SearchEntity;

/**
 * This class performs all the business logic for fetching search results and
 * consolidating them to be sent as a SearchEntity object. It accesses the
 * other services via the respective business factories.
 * 
 * @author Saurabh Mhatre
 */
public class SearchBusinessFactory {
	// This is a constant which is assigned to a answer string for preparing
	// a dummy answer object for questions which have not been answered yet.
	private final String NO_ANSWER = "This question has not been answered yet!";

	private QuestionBusinessFactory quesBusinessFactory;
	private AnswerBusinessFactory ansBusinessFactory;

	/**
	 * This sets the other business factory objects to the respective instance
	 * variables which are then used to communicate with other modules.
	 * 
	 * @param ansBusinessFactory	the answer factory to set.
	 * @param quesBusinessFactory	the question factory to set.
	 */
	public void setBusinessFactories(AnswerBusinessFactory ansBusinessFactory,
			QuestionBusinessFactory quesBusinessFactory) {
		this.ansBusinessFactory = ansBusinessFactory;
		this.quesBusinessFactory = quesBusinessFactory;
	}

	/**
	 * This fetches questions and/or answers depending upon the passed criteria.
	 * It also returns the count of questions and answers which match the
	 * criteria along with the matched questions and/or answers.
	 * 
	 * @param type              this determines where the keyword is to be searched for.
	 * @param category          this determines the category of questions to search for.
	 * @param keyword           the word to be searched for in questions and/or answers.
	 *
	 * @throws ForumException   the wrapped exception thrown during processing.
	 *
	 * @return A SearchEntity instance which encapsulates matched questions and/or answers and counts.
	 */
	@SuppressWarnings("unchecked")
	public SearchEntity getSearchResults(String type, String category, String keyword) throws ForumException {
		String[] searchCriteria = { keyword, category, type };
		SearchValidationFactory.validateSearchCriteria(searchCriteria);
		List<Object> searchResults = new ArrayList<Object>();
		SearchEntity searchEntity = new SearchEntity();
		Map<String, Integer> count = new HashMap<String, Integer>();
		Type typeSwitch = Type.valueOf(type.toUpperCase());
		switch (typeSwitch) {
		case QUESTION:
			searchResults = quesBusinessFactory.getQuestionsWithMostLikedAns(searchCriteria);
			searchEntity.setQuestions((List<QuestionEntity>) searchResults.get(0));
			searchEntity.setAnswers((List<AnswerEntity>) searchResults.get(1));
			count.put("questions", ((List<QuestionEntity>) searchResults.get(0)).size());
			count.put("answers", 0);
			break;
		case ANSWER:
			searchResults = ansBusinessFactory.getAnswersByCriteria(searchCriteria);
			searchEntity.setQuestions((List<QuestionEntity>) searchResults.get(0));
			searchEntity.setAnswers((List<AnswerEntity>) searchResults.get(1));
			count.put("answers", ((List<AnswerEntity>) searchResults.get(0)).size());
			count.put("questions", 0);
			break;
		case ALL:
			List<QuestionEntity> questionsList = new ArrayList<QuestionEntity>();
			List<AnswerEntity> answersList = new ArrayList<AnswerEntity>();
			searchResults = quesBusinessFactory.getQuestionsWithMostLikedAns(searchCriteria);
			count.put("questions", ((List<QuestionEntity>) searchResults.get(0)).size());
			List<QuestionEntity> tempQuesList = (List<QuestionEntity>) searchResults.get(0);
			questionsList.addAll(tempQuesList);
			List<AnswerEntity> tempAnsList = (List<AnswerEntity>) searchResults.get(1);
			answersList.addAll(tempAnsList);
			searchResults.clear();
			searchResults = ansBusinessFactory.getAnswersByCriteria(searchCriteria);
			count.put("answers", ((List<AnswerEntity>) searchResults.get(0)).size());
			tempQuesList = (List<QuestionEntity>) searchResults.get(0);
			tempAnsList = (List<AnswerEntity>) searchResults.get(1);
			// excluding results already fetched
			Integer innerIndex = null;
			Integer outerIndex = null;
			Integer innerResults = questionsList.size();
			Integer outerResults = tempQuesList.size();
			for (outerIndex = 0; outerIndex < outerResults; outerIndex++) {
				for (innerIndex = 0; innerIndex < innerResults; innerIndex++) {
					if (answersList.get(innerIndex).getAns().equals(NO_ANSWER)) {
						continue;
					} else if (questionsList.get(innerIndex).getQuesId().equals(tempQuesList.get(outerIndex).getQuesId())
							&& answersList.get(innerIndex).getAnsId().equals(tempAnsList.get(outerIndex).getAnsId())) {
						break;
					}
				}
				if (innerIndex.equals(innerResults)) {
					questionsList.add(tempQuesList.get(outerIndex));
					answersList.add(tempAnsList.get(outerIndex));
				}
			}
			searchResults.clear();
			searchEntity.setQuestions(questionsList);
			searchEntity.setAnswers(answersList);
			break;
		}
		searchEntity.setCounter(count);
		return searchEntity;
	}

}
