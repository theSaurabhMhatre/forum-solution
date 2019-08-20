package com.forum.app.util;

import java.util.Arrays;

import com.forum.app.util.FileUtility.Mode;
import com.forum.mod.question.service.QuestionEntity.Category;
import com.forum.mod.user.service.UserEntity.Attribute;

/**
 * Utility class used for performing various validation operations on objects.
 * 
 * @author Saurabh Mhatre
 */
public class ValidationUtility {

	/**
	 * This describes the different types of values which can be assigned
	 * to the type parameter passed along with the search API.
	 * 
	 * @author Saurabh Mhatre
	 */
	public enum Type {
		QUESTION("QUESTION"),

		ANSWER("ANSWER"),

		ALL("ALL");

		private String value;

		private Type(String type) {
			this.value = type;
		}

		public String getType() {
			return this.value;
		}

	};

	/**
	 * This checks if a given object is null.
	 * 
	 * @param object the object to be validated.
	 *
	 * @return A Boolean instance (true/false) indicating if the object is null or not null.
	 */
	public static Boolean isNull(Object object) {
		if (object == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This checks if a given object is not null.
	 * 
	 * @param object the object to be validated.
	 *
	 * @return A Boolean instance (true/false) indicating if the object is not null or null.
	 */
	public static Boolean isNotNull(Object object) {
		if (object != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This checks if the given category is valid.
	 * 
	 * @param category the category to be validated.
	 *
	 * @return A Boolean instance (true/false) indicating if the category is valid or not.
	 */
	public static Boolean validateCategory(String category) {
		try {
			return (Arrays.asList(Category.values()).contains(Category.valueOf(category.toUpperCase())) == false);
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * This checks if the given type is valid.
	 * 
	 * @param type the type to be validated.
	 *
	 * @return A Boolean instance (true/false) indicating if the type is valid or not.
	 */
	public static Boolean validateType(String type) {
		try {
			return (Arrays.asList(Type.values()).contains(Type.valueOf(type.toUpperCase())) == false);
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * This checks if the given attribute is valid.
	 * 
	 * @param attribute the attribute to be validated.
	 *
	 * @return A Boolean instance (true/false) indicating if the attribute is valid or not.
	 */
	public static Boolean validateAttribute(String attribute) {
		try {
			return (Arrays.asList(Attribute.values()).contains(Attribute.valueOf(attribute.toUpperCase())) == false);
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * This checks if the given mode is valid.
	 * 
	 * @param mode the mode to be validated.
	 *
	 * @return A Boolean instance (true/false) indicating if the mode is valid or not.
	 */
	public static Boolean validateMode(String mode) {
		try {
			return (Arrays.asList(Mode.values()).contains(Mode.valueOf(mode.toUpperCase())) == false);
		} catch (Exception ex) {
			return false;
		}
	}

}
