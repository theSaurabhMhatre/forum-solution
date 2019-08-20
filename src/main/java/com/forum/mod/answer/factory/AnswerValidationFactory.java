package com.forum.mod.answer.factory;

import org.apache.commons.lang3.StringUtils;

import com.forum.app.constant.ForumValidation;
import com.forum.app.exception.ForumException;
import com.forum.app.util.ValidationUtility;
import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.answer.service.AnswerLikeEntity;

/**
 * This class performs validation of the input data received by the answer 
 * APIs before it is processed by the AnswerBusinessFactory, throws
 * validation exception if data is invalid.   
 * 
 * @author Saurabh Mhatre
 */
public class AnswerValidationFactory {

	public static void validateAnswerId(Long ansId) throws ForumException {
		Boolean check = false;
		check = ValidationUtility.isNull(ansId);
		if (check) {
			throw new ForumException(ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public static void validateAddAnswer(AnswerEntity answer) throws ForumException {
		Boolean check = false;
		check = ValidationUtility.isNull(answer);
		check = check == true ? check : ValidationUtility.isNotNull(answer.getAnsId());
		check = check == true ? check : ValidationUtility.isNull(answer.getQuesId());
		check = check == true ? check : ValidationUtility.isNull(answer.getAnsweredBy());
		check = check == true ? check : StringUtils.isEmpty(answer.getAns());
		if (check) {
			throw new ForumException(ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public static void validateUpdateAnswer(Long ansId, AnswerEntity answer) throws ForumException {
		Boolean check = false;
		check = ValidationUtility.isNull(answer);
		check = check == true ? check : ValidationUtility.isNull(ansId);
		check = check == true ? check : ValidationUtility.isNull(answer.getAnsId());
		check = check == true ? check : ValidationUtility.isNull(answer.getQuesId());
		check = check == true ? check : ValidationUtility.isNull(answer.getAnsweredBy());
		check = check == true ? check : StringUtils.isEmpty(answer.getAns());
		check = check == true ? check : ansId.equals(answer.getAnsId()) == false;
		if (check) {
			throw new ForumException(ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public static void validateLikeOperation(Long ansId, AnswerLikeEntity ansLike) throws ForumException {
		Boolean check = false;
		check = ValidationUtility.isNull(ansId);
		check = check == true ? check : ValidationUtility.isNull(ansLike);
		check = check == true ? check : ValidationUtility.isNull(ansLike.getAnsId());
		check = check == true ? check : ValidationUtility.isNull(ansLike.getQuesId());
		check = check == true ? check : ValidationUtility.isNull(ansLike.getUserId());
		check = check == true ? check : ansId.equals(ansLike.getAnsId()) == false;
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

}
