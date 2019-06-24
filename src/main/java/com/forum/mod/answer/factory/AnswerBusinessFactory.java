package com.forum.mod.answer.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.forum.app.exception.FetchException;
import com.forum.app.exception.ForumException;
import com.forum.app.key.AnswerLikeKey;
import com.forum.app.util.SortUtility;
import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.answer.service.AnswerLikeEntity;
import com.forum.mod.answer.service.AnswerService;
import com.forum.mod.question.factory.QuestionBusinessFactory;
import com.forum.mod.question.factory.QuestionValidationFactory;
import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.question.service.QuestionEntity.Category;
import com.forum.mod.user.factory.UserBusinessFactory;
import com.forum.mod.user.factory.UserValidationFactory;
import com.forum.mod.user.service.UserEntity;

/**
 * This class performs all the business logic before and/or after storing and
 * retrieving data to and from the database for AnswerEntity and
 * AnswerLikeEntity objects. All other business factories communicate with
 * AnswerService via this class.
 * 
 * @author Saurabh Mhatre
 *
 */
public class AnswerBusinessFactory {
	private final String NO_ANSWER = "This question has not been answered yet!";

	private AnswerService ansService;
	private UserBusinessFactory userBusinessFactory;
	private QuestionBusinessFactory quesBusinessFactory;
	
	/**
	 * This is the default constructor, initializes AnswerService. This is the
	 * only place where AnswerService is initialized.
	 * 
	 */
	public AnswerBusinessFactory() {
		ansService = new AnswerService();
	}

	/**
	 * This sets the other business factory objects to the respective instance
	 * variables which are then used to communicate with other modules. 
	 * 
	 * @param UserBusinessFactory userBusinessFactory - user factory to set.
	 * @param QuestionBusinessFactory quesBusinessFactory - question factory to set.
	 */
	public void setBusinessFactories(UserBusinessFactory userBusinessFactory,
			QuestionBusinessFactory quesBusinessFactory) {
		this.userBusinessFactory = userBusinessFactory;
		this.quesBusinessFactory = quesBusinessFactory;
	}

	/**
	 * This returns all the answers corresponding to a particular question.
	 * Also sorts all the answers in the descending order of likes.
	 * 
	 * @param Long quesId - question Id for which answers are to be fetched.
	 * @return Set<AnswerEntity> sortedAnswers - answers corresponding to quesId.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public Set<AnswerEntity> getAnswersByQuestion(Long quesId) throws ForumException {
		QuestionValidationFactory.validateQuestionId(quesId);
		List<AnswerEntity> answers = ansService.getAnswersByQuestion(quesId);
		List<Object> ansLikes = ansService.getLikesByAnswersByQuestion(quesId);
		for (AnswerEntity answer : answers) {
			answer.setQuesId(answer.getQuestion().getQuesId());
			answer.setAnsweredBy(answer.getUser().getUserId());
			for (Object ansLike : ansLikes) {
				Object[] currentAnsLike = (Object[]) ansLike;
				Long ansId = (Long) currentAnsLike[1];
				if (answer.getAnsId().equals(ansId)) {
					Long likes = (Long) currentAnsLike[0];
					answer.setLikes(likes);
					break;
				}
				answer.setLikes(0L);
			}
			if (answer.getLikes() == null) {
				answer.setLikes(0L);
			}
		}
		Set<AnswerEntity> sortedAnswers = SortUtility.sortAnswers(answers);
		return sortedAnswers;
	}

	/**
	 * This adds an answer to the DB if validation is successful.
	 * 
	 * @param AnswerEntity answer - object to be added.
	 * @return AnswerEntity answer - added object.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public AnswerEntity addAnswer(AnswerEntity answer) throws ForumException {
		AnswerValidationFactory.validateAddAnswer(answer);
		Long userId = answer.getAnsweredBy();
		Long quesId = answer.getQuesId();
		UserEntity user = userBusinessFactory.getUser(userId);
		QuestionEntity question = quesBusinessFactory.getQuestion(quesId);
		answer.setUser(user);
		answer.setQuestion(question);
		answer.setLikes(0L);
		answer.setAnsCreatedOn(new Date());
		answer.setAnsModifiedOn(new Date());
		answer = ansService.modifyAnswer(answer);
		return answer;
	}

	/**
	 * This modifies an existing answer if validation is successful.
	 * 
	 * @param Long ansId - answer Id of answer to be modified.
	 * @param AnswerEntity answer - object to be modified.
	 * @return AnswerEntity answer - modified object.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public AnswerEntity updateAnswer(Long ansId, AnswerEntity answer) throws ForumException {
		AnswerValidationFactory.validateUpdateAnswer(ansId, answer);
		Long userId = answer.getAnsweredBy();
		Long quesId = answer.getQuesId();
		UserEntity user = userBusinessFactory.getUser(userId);
		QuestionEntity question = quesBusinessFactory.getQuestion(quesId);
		answer.setUser(user);
		answer.setQuestion(question);
		answer.setAnsModifiedOn(new Date());
		answer = ansService.modifyAnswer(answer);
		Long likes = ansService.getLikesByAnswer(ansId);
		answer.setLikes(likes);
		return answer;
	}

	/**
	 * This deletes the answer corresponding to the passed answer Id. It also
	 * deletes all the likes corresponding to the answer.
	 * 
	 * @param Long ansId - Id of answer to be deleted.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public void deleteAnswer(Long ansId) throws ForumException {
		AnswerValidationFactory.validateAnswerId(ansId);
		ansService.deleteAnsLikesByAnsId(ansId);
		ansService.deleteAnswer(ansId);
	}

	/**
	 * This is used to like an answer i.e., inserts an entry for every answer liked.
	 * 
	 * @param Long ansId - Id of answer to be liked.
	 * @param AnswerLikeEntity ansLike - object containing like information. 
	 * @return AnswerLikeEntity ansLike - DB entry for the answer liked.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public AnswerLikeEntity likeAnswer(Long ansId, AnswerLikeEntity ansLike) throws ForumException {
		AnswerValidationFactory.validateLikeOperation(ansId, ansLike);
		Long userId = ansLike.getUserId();
		Long quesId = ansLike.getQuesId();
		UserEntity user = userBusinessFactory.getUser(userId);
		QuestionEntity ques = quesBusinessFactory.getQuestion(quesId);
		AnswerEntity ans = ansService.getAnswer(ansLike.getAnsId());
		AnswerLikeKey key = new AnswerLikeKey();
		key.setUser(user);
		key.setQuestion(ques);
		key.setAnswer(ans);
		ansLike.setAnsLikeKey(key);
		ansLike.setAnsLikedOn(new Date());
		key = ansService.likeAnswer(ansLike);
		ansLike.setUserId(key.getUser().getUserId());
		ansLike.setQuesId(key.getQuestion().getQuesId());
		ansLike.setAnsId(key.getAnswer().getAnsId());
		return ansLike;
	}

	/**
	 * This is used to dislike an answer i.e., deletes an entry for every
	 * answer disliked.
	 * 
	 * @param Long ansId - Id of answer to be disliked.
	 * @param AnswerLikeEntity ansLike - object containing like information. 
	 * @return
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public void dislikeAnswer(Long ansId, AnswerLikeEntity ansLike) throws ForumException {
		AnswerValidationFactory.validateLikeOperation(ansId, ansLike);
		Long userId = ansLike.getUserId();
		Long quesId = ansLike.getQuesId();
		UserEntity user = userBusinessFactory.getUser(userId);
		QuestionEntity ques = quesBusinessFactory.getQuestion(quesId);
		AnswerEntity ans = ansService.getAnswer(ansLike.getAnsId());
		AnswerLikeKey key = new AnswerLikeKey();
		key.setUser(user);
		key.setQuestion(ques);
		key.setAnswer(ans);
		ansLike.setAnsLikeKey(key);
		ansService.dislikeAnswer(ansLike);
		return;
	}

	/**
	 * This returns the most liked answer corresponding to every question in the 
	 * list of questions passed to this method. Returns a dummy answer object if
	 * a question has not been answered yet.
	 * 
	 * @param List<Long> quesIds - list of questions for which answers are to be fetched.
	 * @return List<Object> ansByQuestions - answers corresponding to quesIds.
	 * @throws FetchException - wrapped exception thrown during processing.
	 */
	public List<Object> getMostLikedAnsByQuestions(List<Long> quesIds) throws FetchException {
		List<Object> ansByQuestionsTemp = ansService.getMostLikedAnsByQuestions();
		List<Object> ansByQuestions = new ArrayList<Object>();
		outer: for (Long quesId : quesIds) {
			for (Object current : ansByQuestionsTemp) {
				Object[] currentRec = (Object[]) current;
				String temp = currentRec[1].toString();
				Long currQuesId = Long.parseLong(temp);
				if (quesId.equals(currQuesId)) {
					temp = currentRec[0].toString();
					Long currAnsId = Long.parseLong(temp);
					temp = currentRec[2].toString();
					Long currLikes = Long.parseLong(temp);
					AnswerEntity ans = ansService.getAnswer(currAnsId);
					ans.setAnsweredBy(ans.getUser().getUserId());
					ans.setQuesId(ans.getQuestion().getQuesId());
					ans.setLikes(currLikes);
					ansByQuestions.add(ans);
					continue outer;
				}
			}
			// current question has no answer
			// or answer(s) do(es) not have any likes
			// fetch answer separately
			try {
				AnswerEntity ans = ansService.getAnsByQuesId(quesId);
				ans.setAnsweredBy(ans.getUser().getUserId());
				ans.setQuesId(ans.getQuestion().getQuesId());
				ans.setLikes(0L);
				ansByQuestions.add(ans);
			} catch (ForumException ex) {
				AnswerEntity ans = new AnswerEntity();
				ans.setAnsId(0L);
				ans.setAns(NO_ANSWER);
				ans.setAnsweredBy(0L);
				ans.setQuesId(0L);
				ans.setLikes(0L);
				ansByQuestions.add(ans);
			}
		}
		return ansByQuestions;
	}
	
	/**
	 * This fetches all or some answers depending on whether the search criteria 
	 * is specified or not. Also sets the question object for every answer fetched. 
	 * 
	 * @param String[] searchCriteria - optional criteria object.
	 * @return List<Object> result - all answers or the ones matching searchCriteria. 
	 * @throws ForumException exception - wrapped exception thrown during processing. 
	 */
	public List<Object> getAnswersByCriteria(String... searchCriteria) throws ForumException {
		AnswerValidationFactory.validateSearchCriteria(searchCriteria);
		// default values to be used when fetching all answers
		String keyword = "%%";
		String category = "%%";
		if (searchCriteria.length > 0) {
			keyword = "%" + searchCriteria[0] + "%";
			category = searchCriteria[1].equals(Category.ALL.getCategory()) ? category : searchCriteria[1];
		}
		List<Long> quesIds = new ArrayList<Long>();
		List<QuestionEntity> questions = new ArrayList<QuestionEntity>();
		List<AnswerEntity> answers = ansService.getAnswers(keyword, category);
		List<Object> ansLikes = ansService.getLikesByAnswers(keyword, category);
		for (AnswerEntity ans : answers) {
			QuestionEntity ques = ans.getQuestion();
			UserEntity user = ans.getUser();
			ans.setAnsweredBy(user.getUserId());
			ans.setQuesId(ques.getQuesId());
			questions.add(ques);
			quesIds.add(ques.getQuesId());
			for (Object ansLike : ansLikes) {
				Object[] currentAnsLike = (Object[]) ansLike;
				Long ansId = (Long) currentAnsLike[1];
				if (ans.getAnsId().equals(ansId)) {
					Long likes = (Long) currentAnsLike[0];
					ans.setLikes(likes);
					break;
				}
				ans.setLikes(0L);
			}
			if (ans.getLikes() == null) {
				ans.setLikes(0L);
			}
		}
		Set<AnswerEntity> sortedAnswers = SortUtility.sortAnswers(answers);
		List<Object> answersList = Arrays.asList(sortedAnswers.toArray());
		List<Object> quesLikes = quesBusinessFactory.getLikesBySpecificQuestions(quesIds);
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
		List<QuestionEntity> questionsList = new ArrayList<QuestionEntity>();
		for (Object answer : answersList) {
			AnswerEntity current = (AnswerEntity) answer;
			for (QuestionEntity ques : questions) {
				Long quesId = ques.getQuesId();
				if (current.getQuesId().equals(quesId)) {
					questionsList.add(ques);
					break;
				}
			}
		}
		List<Object> result = new ArrayList<Object>();
		result.add(questionsList);
		result.add(answersList);
		return result;
	}

	/**
	 * This fetches Id's of all the answers liked by a user.
	 * 
	 * @param Long userId - Id of user for which liked answers are to be fetched.
	 * @return List<Integer> answerLikes - Id's of all answers liked by userId.
	 * @throws ForumException exception - wrapped exception thrown during processing. 
	 */
	public List<Integer> getAnswersLikedByUser(Long userId) throws ForumException {
		UserValidationFactory.validateUserId(userId);
		List<Integer> answerLikes = ansService.getAnswersLikedByUser(userId);
		return answerLikes;
	}
	
	/**
	 * This fetches the total number of likes received by all the answers
	 * answered by a user. 
	 * 
	 * @param userId - Id of the user, the likes for whose answers are to be fetched.
	 * @return Long totalLikes - likes received by answers answered by userId.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public Long getUsersAnswersLikes(Long userId) throws ForumException {
		UserValidationFactory.validateUserId(userId);
		Long totalLikes = ansService.getUsersAnswersLikes(userId);
		return totalLikes;
	}

	/**
	 * This fetches the mapping between the likes received for all the answers 
	 * answered by a particular user and the user for all the registered users.   
	 * 
	 * @return List<Object> ansLikesByUsers - mapping between likes and users.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public List<Object> getAllUsersAnswersLikes() throws ForumException {
		List<Object> ansLikesByUsers = ansService.getAllUsersAnswersLikes();
		return ansLikesByUsers;
	}

	/**
	 * This fetches all answers answered by a user.
	 * 
	 * @param Long userId - Id of the user for which answers are to be fetched.
	 * @return List<AnswerEntity> answers - answers answered by userId.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public List<AnswerEntity> getAnswersByUser(Long userId) throws ForumException {
		UserValidationFactory.validateUserId(userId);
		List<AnswerEntity> answers = ansService.getAnswersByUser(userId);
		return answers;
	}

	/**
	 * This deletes all the answer likes corresponding to a user.  
	 * 
	 * @param Long userId - Id of the user whose answer likes are to be deleted.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public void deleteAnsLikesByUserId(Long userId) throws ForumException {
		UserValidationFactory.validateUserId(userId);
		ansService.deleteAnsLikesByUserId(userId);
	}

	/**
	 * This deletes all the answers corresponding to a question.
	 * 
	 * @param Long quesId - Id of the question, the answers for which are to be deleted.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public void deleteAnswerByQuesId(Long quesId) throws ForumException {
		QuestionValidationFactory.validateQuestionId(quesId);
		ansService.deleteAnsLikesByQuesId(quesId);
		ansService.deleteAnsByQuesId(quesId);
	}

	/**
	 * This fetches the question answer pair for the specified Id's if it
	 * exists.
	 * 
	 * @param Long ansId - Id of the answer which is part of the pair.
	 * @param Long quesId - Id of the question which is part of the pair.
	 * @return Map<String, Object> pairExists - object representing the pair existence.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public Map<String, Object> getAnswerByQuesAnsPair(Long ansId, Long quesId) throws ForumException {
		AnswerValidationFactory.validateAnswerId(ansId);
		QuestionValidationFactory.validateQuestionId(quesId);
		AnswerEntity answer = ansService.getAnswerByQuesAnsPair(ansId, quesId);
		Map<String, Object> pairExists = new HashMap<String, Object>();
		pairExists.put("exists", true);
		pairExists.put("answer", answer);
		return pairExists;
	}

}
