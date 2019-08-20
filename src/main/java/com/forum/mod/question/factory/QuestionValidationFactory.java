package com.forum.mod.question.factory;

import org.apache.commons.lang3.StringUtils;

import com.forum.app.constant.ForumValidation;
import com.forum.app.exception.ForumException;
import com.forum.app.util.ValidationUtility;
import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.question.service.QuestionLikeEntity;

/**
 * This class performs validation of the input data received by the question 
 * APIs before it is processed by the QuestionBusinessFactory, throws
 * validation exception if data is invalid.   
 * 
 * @author Saurabh Mhatre
 */
public class QuestionValidationFactory {

	public static void validateQuestionId(Long quesId) throws ForumException {
		Boolean check = false;
		check = ValidationUtility.isNull(quesId);
		if (check) {
			throw new ForumException(ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public static void validateSearchCriteria(String... searchCriteria) throws ForumException {
		if (searchCriteria.length > 0) {
			Boolean check = false;
			check = StringUtils.isEmpty(searchCriteria[0]);
			check = check == true ? check : StringUtils.isEmpty(searchCriteria[1]);
			check = check == true ? check : StringUtils.isEmpty(searchCriteria[2]);
			check = check == true ? check : ValidationUtility.validateCategory(searchCriteria[1]);
			check = check == true ? check : ValidationUtility.validateType(searchCriteria[2]);
			if (check) {
				throw new ForumException(ForumValidation.VALIDATION_FAILURE.getMessage());
			}
		}
	}

	public static void validateAddQuestion(QuestionEntity question) throws ForumException {
		Boolean check = false;
		check = ValidationUtility.isNull(question);
		check = check == true ? check : ValidationUtility.isNotNull(question.getQuesId());
		check = check == true ? check : StringUtils.isEmpty(question.getQues());
		check = check == true ? check : StringUtils.isEmpty(question.getCategory());
		check = check == true ? check : ValidationUtility.isNull(question.getAskedBy());
		check = check == true ? check : ValidationUtility.validateCategory(question.getCategory());
		if (check) {
			throw new ForumException(ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public static void validateUpdateQuestion(Long quesId, QuestionEntity question) throws ForumException {
		Boolean check = false;
		check = ValidationUtility.isNull(question);
		check = check == true ? check : ValidationUtility.isNull(quesId);
		check = check == true ? check : ValidationUtility.isNull(question.getQuesId());
		check = check == true ? check : StringUtils.isEmpty(question.getQues());
		check = check == true ? check : StringUtils.isEmpty(question.getCategory());
		check = check == true ? check : ValidationUtility.isNull(question.getAskedBy());
		check = check == true ? check : quesId.equals(question.getQuesId()) == false;
		check = check == true ? check : ValidationUtility.validateCategory(question.getCategory());
		if (check) {
			throw new ForumException(ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public static void validateLikeOperation(Long quesId, QuestionLikeEntity quesLike) throws ForumException {
		Boolean check = false;
		check = ValidationUtility.isNull(quesId);
		check = check == true ? check : ValidationUtility.isNull(quesLike);
		check = check == true ? check : ValidationUtility.isNull(quesLike.getQuesId());
		check = check == true ? check : ValidationUtility.isNull(quesLike.getUserId());
		check = check == true ? check : quesId.equals(quesLike.getQuesId()) == false;
		if (check) {
			throw new ForumException(ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

}
