package com.forum.mod.question.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.forum.app.exception.ForumException;
import com.forum.app.key.QuestionLikeKey;
import com.forum.app.util.SortUtility;
import com.forum.mod.answer.factory.AnswerBusinessFactory;
import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.question.service.QuestionEntity.Category;
import com.forum.mod.question.service.QuestionLikeEntity;
import com.forum.mod.question.service.QuestionService;
import com.forum.mod.user.factory.UserBusinessFactory;
import com.forum.mod.user.factory.UserValidationFactory;
import com.forum.mod.user.service.UserEntity;

/**
 * This class performs all the business logic before and/or after storing
 * and retrieving data to and from the database for QuestionEntity and
 * QuestionLikeEntity objects. All other business factories communicate with
 * QuestionService via this class.
 * 
 * @author Saurabh Mhatre
 *
 */
public class QuestionBusinessFactory {
	private QuestionService questionService;
	private UserBusinessFactory userBusinessFactory;
	private AnswerBusinessFactory ansBusinessFactory;

	/**
	 * This is the default constructor, initializes QuestionService. This is the
	 * only place where QuestionService is initialized.
	 * 
	 */
	public QuestionBusinessFactory() {
		questionService = new QuestionService();
	}
	
	/**
	 * This sets the other business factory objects to the respective instance
	 * variables which are then used to communicate with other modules.
	 * 
	 * @param UserBusinessFactory userBusinessFactory - user factory to set.
	 * @param AnswerBusinessFactory ansBusinessFactory - answer factory to set.
	 */
	public void setBusinessFactories(UserBusinessFactory userBusinessFactory,
			AnswerBusinessFactory ansBusinessFactory) {
		this.userBusinessFactory = userBusinessFactory;
		this.ansBusinessFactory = ansBusinessFactory;
	}

	/**
	 * This is a helper method which takes a list of question and returns list
	 * after removing all the duplicate entries.
	 * 
	 * @param List<QuestionEntity> questions - list with/without duplicated questions.
	 * @return List<QuestionEntity> distinctQuestions - distinct questions list.
	 */
	public List<QuestionEntity> removeDuplicateQuestions(List<QuestionEntity> questions) {
		Map<Long, QuestionEntity> questionsMap = new HashMap<Long, QuestionEntity>();
		for (QuestionEntity ques : questions) {
			if (questionsMap.containsKey(ques.getQuesId()) == false) {
				questionsMap.put(ques.getQuesId(), ques);
			}
		}
		List<QuestionEntity> distinctQuestions = new ArrayList<QuestionEntity>(questionsMap.values());
		return distinctQuestions;
	}

	/**
	 * This fetches the question corresponding the passed question Id. 
	 * 
	 * @param Long quesId - Id of the question which is to be fetched.
	 * @return QuestionEntity question - question corresponding to quesId.
	 * @throws ForumException exception - wrapped exception thrown during processing. 
	 */
	public QuestionEntity getQuestion(Long quesId) throws ForumException {
		QuestionValidationFactory.validateQuestionId(quesId);
		QuestionEntity question = questionService.getQuestion(quesId);
		question.setAskedBy(question.getUser().getUserId());
		List<Object> quesLikes = getLikesBySpecificQuestions(Arrays.asList(question.getQuesId()));
		if (quesLikes.size() > 0) {
			Object[] quesLike = (Object[]) quesLikes.get(0);
			Long likes = (Long) quesLike[0];
			question.setLikes(likes);
		} else {
			question.setLikes(0L);
		}
		return question;
	}

	/**
	 * This fetches all or some questions depending on whether the search criteria 
	 * is specified or not. Also fetches the best answer i.e., the answer with the 
	 * most likes for every question if the question is answered, sets a dummy answer 
	 * object for questions which are unanswered.
	 * 
	 * @param String[] searchCriteria - optional criteria object.
	 * @return List<Object> result - all questions or the ones matching searchCriteria.
	 * @throws ForumException exception - wrapped exception thrown during processing. 
	 */
	public List<Object> getQuestionsWithMostLikedAns(String... searchCriteria) throws ForumException {
		QuestionValidationFactory.validateSearchCriteria(searchCriteria);
		// default values to be used when fetching all questions
		String keyword = "%%";
		String category = "%%";
		if (searchCriteria.length > 0) {
			keyword = "%" + searchCriteria[0] + "%";
			category = searchCriteria[1].equals(Category.ALL.getCategory()) ? category : searchCriteria[1];
		}
		List<QuestionEntity> questions = questionService.getQuestions(keyword, category);
		List<Object> quesLikes = questionService.getLikesByQuestions(keyword, category);
		for (QuestionEntity ques : questions) {
			ques.setAskedBy(ques.getUser().getUserId());
			for (Object quesLike : quesLikes) {
				Object[] currentQuesLike = (Object[]) quesLike;
				Long quesId = (Long) currentQuesLike[1];
				if (ques.getQuesId().equals(quesId)) {
					Long likes = (Long) currentQuesLike[0];
					ques.setLikes(likes);
					break;
				}
				ques.setLikes(0L);
			}
			if (ques.getLikes() == null) {
				ques.setLikes(0L);
			}
		}
		Set<QuestionEntity> sortedQuestions = SortUtility.sortQuestions(questions);
		List<Object> questionsList = Arrays.asList(sortedQuestions.toArray());
		List<Long> quesIds = new ArrayList<Long>();
		for (Object current : questionsList) {
			QuestionEntity ques = (QuestionEntity) current;
			quesIds.add(ques.getQuesId());
		}
		List<Object> answersList = ansBusinessFactory.getMostLikedAnsByQuestions(quesIds);
		List<Object> result = new ArrayList<Object>();
		result.add(questionsList);
		result.add(answersList);
		return result;
	}

	/**
	 * This adds a question to the DB is validation is successful.
	 * 
	 * @param QuestionEntity question - object to be added.
	 * @return QuestionEntity question - added object.
	 * @throws ForumException exception - wrapped exception thrown during processing. 
	 */
	public QuestionEntity addQuestion(QuestionEntity question) throws ForumException {
		QuestionValidationFactory.validateAddQuestion(question);
		Long userId = question.getAskedBy();
		UserEntity user = userBusinessFactory.getUser(userId);
		question.setUser(user);
		question.setLikes(0L);
		question.setQuesCreatedOn(new Date());
		question.setQuesModifiedOn(new Date());
		question = questionService.modifyQuestion(question);
		return question;
	}

	/**
	 * This modifies an existing question if validation is successful.
	 * 
	 * @param Long quesId - Id of the question to be updated. 
	 * @param QuestionEntity question - object to be modified. 
	 * @return QuestionEntity question - modified object.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public QuestionEntity updateQuestion(Long quesId, QuestionEntity question) throws ForumException {
		QuestionValidationFactory.validateUpdateQuestion(quesId, question);
		Long userId = question.getAskedBy();
		UserEntity user = userBusinessFactory.getUser(userId);
		question.setUser(user);
		question.setQuesModifiedOn(new Date());
		question = questionService.modifyQuestion(question);
		Long likes = questionService.getLikesByQuestion(quesId);
		question.setLikes(likes);
		return question;
	}

	/**
	 * This deletes the question corresponding to the passed question Id. It also
	 * deletes all the answers corresponding those questions, and the likes the
	 * questions and the answers have.  
	 * 
	 * @param Long quesId - Id of answer to be deleted.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public void deleteQuestion(Long quesId) throws ForumException {
		QuestionValidationFactory.validateQuestionId(quesId);
		ansBusinessFactory.deleteAnswerByQuesId(quesId);
		questionService.deleteQuesLikesByQuesId(quesId);
		questionService.deleteQuestion(quesId);
	}

	/**
	 * This is used to like a question i.e., inserts an entry for every question liked.
	 * 
	 * @param Long quesId - Id of question to be liked.
	 * @param QuestionLikeEntity quesLike - object containing like information. 
	 * @return QuestionLikeEntity quesLike - DB entry for the question liked.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public QuestionLikeEntity likeQuestion(Long quesId, QuestionLikeEntity quesLike) throws ForumException {
		QuestionValidationFactory.validateLikeOperation(quesId, quesLike);
		Long userId = quesLike.getUserId();
		QuestionEntity ques = questionService.getQuestion(quesLike.getQuesId());
		UserEntity user = userBusinessFactory.getUser(userId);
		QuestionLikeKey key = new QuestionLikeKey();
		key.setQuestion(ques);
		key.setUser(user);
		quesLike.setQuesLikeKey(key);
		quesLike.setQuesLikedOn(new Date());
		key = questionService.likeQuestion(quesLike);
		quesLike.setQuesId(key.getQuestion().getQuesId());
		quesLike.setUserId(key.getUser().getUserId());
		return quesLike;
	}

	/**
	 * This is used to dislike a question i.e., deletes an entry for every
	 * question disliked.
	 * 
	 * @param Long quesId - Id of question to be disliked.
	 * @param QuestionLikeEntity quesLike - object containing like information. 
	 * @return
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public void dislikeQuestion(Long quesId, QuestionLikeEntity quesLike) throws ForumException {
		QuestionValidationFactory.validateLikeOperation(quesId, quesLike);
		Long userId = quesLike.getUserId();
		QuestionEntity ques = questionService.getQuestion(quesLike.getQuesId());
		UserEntity user = userBusinessFactory.getUser(userId);
		QuestionLikeKey key = new QuestionLikeKey();
		key.setQuestion(ques);
		key.setUser(user);
		quesLike.setQuesLikeKey(key);
		questionService.dislikeQuestion(quesLike);
		return;
	}

	/**
	 * This fetches Id's of all the questions liked by a user.
	 * 
	 * @param Long userId - Id of the user for which liked questions are to be fetched.
	 * @return List<Integer> questionLikes - Id's of all questions liked by userId.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public List<Integer> getQuestionsLikedByUser(Long userId) throws ForumException {
		UserValidationFactory.validateUserId(userId);
		List<Integer> questionLikes = questionService.getQuestionsLikedByUser(userId);
		return questionLikes;
	}

	/**
	 * This fetches the total number of likes received by all questions asked by 
	 * a user.
	 * 
	 * @param Long userId - Id of the user, the likes for whose questions are to be fetched.
	 * @return Long totalLikes - likes received by questions asked by userId.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public Long getUsersQuestionsLikes(Long userId) throws ForumException {
		UserValidationFactory.validateUserId(userId);
		Long totalLikes = questionService.getUsersQuestionsLikes(userId);
		return totalLikes;
	}

	/**
	 * This fetches all questions asked by a user.
	 * 
	 * @param Long userId - Id of the user for which questions are to be fetched.
	 * @param Boolean withLikes - used to decide if likes are to be fetched or not.
	 * @return List<QuestionEntity> questions - questions asked by userId.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public List<QuestionEntity> getQuestionsByUser(Long userId, Boolean withLikes) throws ForumException {
		UserValidationFactory.validateUserId(userId);
		List<QuestionEntity> questions = questionService.getQuestionsByUser(userId);
		if (withLikes) {
			List<Object> quesLikes = questionService.getLikesByQuestionsByUser(userId);
			for (QuestionEntity ques : questions) {
				ques.setAskedBy(ques.getUser().getUserId());
				for (Object quesLike : quesLikes) {
					Object[] currentQuesLike = (Object[]) quesLike;
					Long quesId = (Long) currentQuesLike[1];
					if (ques.getQuesId().equals(quesId)) {
						Long likes = (Long) currentQuesLike[0];
						ques.setLikes(likes);
						break;
					}
					ques.setLikes(0L);
				}
				if (ques.getLikes() == null) {
					ques.setLikes(0L);
				}
			}
		}
		return questions;
	}

	/**
	 * This fetches all the questions answered by a user.
	 * 
	 * @param Long userId - Id of the user for which answered questions are to be fetched. 
	 * @return List<Object> answeredQuestions - questions answered by userId.
	 * @throws ForumException exception - wrapped exception thrown during processing. 
	 */
	public List<Object> getQuestionsAnsweredByUser(Long userId) throws ForumException {
		UserValidationFactory.validateUserId(userId);
		List<Long> quesIds = new ArrayList<Long>();
		List<QuestionEntity> questions = new ArrayList<QuestionEntity>();
		Map<Long, AnswerEntity> quesAnsMap = new HashMap<Long, AnswerEntity>();
		List<AnswerEntity> answers = ansBusinessFactory.getAnswersByUser(userId);
		for (AnswerEntity answer : answers) {
			UserEntity askedBy = answer.getQuestion().getUser();
			answer.getQuestion().setAskedBy(askedBy.getUserId());
			quesIds.add(answer.getQuestion().getQuesId());
			questions.add(answer.getQuestion());
			quesAnsMap.put(answer.getQuestion().getQuesId(), answer);
		}
		List<Object> quesLikes = questionService.getLikesBySpecificQuestions(quesIds);
		for (QuestionEntity ques : questions) {
			ques.setAskedBy(ques.getUser().getUserId());
			for (Object quesLike : quesLikes) {
				Object[] currentQuesLike = (Object[]) quesLike;
				Long quesId = (Long) currentQuesLike[1];
				if (ques.getQuesId().equals(quesId)) {
					Long likes = (Long) currentQuesLike[0];
					ques.setLikes(likes);
					break;
				}
				ques.setLikes(0L);
			}
			if (ques.getLikes() == null) {
				ques.setLikes(0L);
			}
		}
		answers.clear();
		List<QuestionEntity> distinctQuestions = removeDuplicateQuestions(questions);
		for (QuestionEntity question : distinctQuestions) {
			answers.add(quesAnsMap.get(question.getQuesId()));
		}
		List<Object> answeredQuestions = new ArrayList<Object>();
		answeredQuestions.add(distinctQuestions);
		answeredQuestions.add(answers);
		return answeredQuestions;
	}

	/**
	 * This fetches the mapping between the likes received for all the questions 
	 * asked by a particular user and the user for all the registered users.
	 * 
	 * @return List<Object> quesLikesByUsers - mapping between likes and users.
	 * @throws ForumException exception - wrapped exception thrown during processing. 
	 */
	public List<Object> getAllUsersQuestionsLikes() throws ForumException {
		List<Object> quesLikesByUsers = questionService.getAllUsersQuestionsLikes();
		return quesLikesByUsers;
	}

	/**
	 * This deletes all the question likes corresponding to a user.
	 * 
	 * @param Long userId - Id of the user whose question likes are to be deleted.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public void deleteQuesLikesByUserId(Long userId) throws ForumException {
		UserValidationFactory.validateUserId(userId);
		questionService.deleteQuesLikesByUserId(userId);
	}

	/**
	 * This fetches the likes for the list of question Id's passed.
	 * 
	 * @param List<Long> quesIds - Id's for which the likes are to be fetched.
	 * @return List<Object> questionLikes - likes corresponding to quesIds.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public List<Object> getLikesBySpecificQuestions(List<Long> quesIds) throws ForumException {
		for (Long quesId : quesIds) {
			QuestionValidationFactory.validateQuestionId(quesId);
		}
		List<Object> questionLikes = questionService.getLikesBySpecificQuestions(quesIds);
		return questionLikes;
	}

}
