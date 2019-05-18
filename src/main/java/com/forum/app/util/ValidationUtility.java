package com.forum.app.util;

import java.util.Arrays;

import com.forum.mod.question.service.QuestionEntity.Category;
import com.forum.mod.user.service.UserEntity.Attribute;

public class ValidationUtility {

	public enum Type {
		QUESTION("QUESTION"),
		ANSWER("ANSWER"),
		ALL("ALL");
		
		private String value;
		
		private Type(String type){
			this.value = type;
		}
		
		public String getType() {
			return this.value;
		}
		
	};
	
	public static Boolean isNull(Object object) {
		if(object == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Boolean isNotNull(Object object) {
		if(object != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Boolean validateCategory(String category) {
		try {
			return (Arrays.asList(Category.values())
					.contains(Category.valueOf(category.toUpperCase())) == false);
		} catch(Exception ex) {
			return false;
		}
	}

	public static Boolean validateType(String type) {
		try {
			return (Arrays.asList(Type.values())
					.contains(Type.valueOf(type.toUpperCase())) == false);
		} catch(Exception ex) {
			return false;
		}
	}

	public static Boolean validateAttribute(String attribute) {
		try {
			return (Arrays.asList(Attribute.values())
					.contains(Attribute.valueOf(attribute.toUpperCase())) == false);
		} catch(Exception ex) {
			return false;
		}
	}
	
}
