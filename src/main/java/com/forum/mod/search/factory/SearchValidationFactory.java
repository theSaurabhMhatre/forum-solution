package com.forum.mod.search.factory;

import org.apache.commons.lang3.StringUtils;

import com.forum.app.constant.ForumValidation;
import com.forum.app.exception.ForumException;
import com.forum.app.util.ValidationUtility;

/**
 * This class performs validation of the input data received by the search 
 * API(s) before it is processed by the SearchBusinessFactory, throws
 * validation exception if data is invalid.   
 * 
 * @author Saurabh Mhatre
 *
 */
public class SearchValidationFactory {

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
