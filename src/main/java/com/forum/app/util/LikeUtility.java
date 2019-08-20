package com.forum.app.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class to perform various likes related operations.
 * 
 * @author Saurabh Mhatre
 */
public class LikeUtility {
	public static final Integer COLUMNS = 2;

	/**
	 * Maps likes to the corresponding users, maps 0 to a user if no likes are
	 * present corresponding to the user. Assumes users to have Id's in order. 
	 * 
	 * @param likes			the likes corresponding to all users.
	 * @param totalUsers	the total number of registered users.
	 *
	 * @return A Long[][] instance which holds the mapping between likes and users.
	 *
	 * @deprecated This method is no longer being used. Please refer {@link #normalizeLikes(List, List)}.
	 */
	@Deprecated
	public static Long[][] normalizeLikesArray(List<Object> likes, Integer totalUsers) {
		Long[][] normalizedLikes = new Long[totalUsers + 1][COLUMNS];
		Object[] likeArray = null;
		Long temp = null;
		Integer index = null;
		for (index = 1; index <= totalUsers; index++) {
			Arrays.fill(normalizedLikes[index], 0L);
		}
		for (Object currentLike : likes) {
			likeArray = (Object[]) currentLike;
			temp = Long.parseLong(likeArray[1].toString());
			index = Integer.parseInt(temp.toString());
			normalizedLikes[index][0] = Long.parseLong(temp.toString());
			normalizedLikes[index][1] = Long.parseLong(likeArray[0].toString());
		}
		return normalizedLikes;
	}

	/**
	 * Maps likes to the corresponding users, maps 0 to a user if no likes are
	 * present corresponding to the user.
	 * 
	 * @param likes		the likes corresponding to all users.
	 * @param userIds	the list of registered user Id's.
	 *
	 * @return A Map instance which holds the mapping between likes and users.
	 */
	public static Map<Long, Long> normalizeLikes(List<Object> likes, List<Long> userIds) {
		Map<Long, Long> normalizedLikes = new HashMap<Long, Long>();
		Object[] likeArray = null;
		Long userId = null;
		Long userLikes = null;
		for (Object currentLike : likes) {
			likeArray = (Object[]) currentLike;
			userId = Long.parseLong(likeArray[1].toString());
			userLikes = Long.parseLong(likeArray[0].toString());
			normalizedLikes.put(userId, userLikes);
		}
		for (Long currentUserId : userIds) {
			if (normalizedLikes.containsKey(currentUserId)) {
				continue;
			} else {
				normalizedLikes.put(currentUserId, 0L);
			}
		}
		return normalizedLikes;
	}

	/**
	 * Sorts the List passed according to the total number of likes
	 * present at the 4th index in descending order.
	 * 
	 * @param userRankings the list of details of all the registered users.
	 */
	public static void sortLikes(List<List<Object>> userRankings) {
		Collections.sort(userRankings, new Comparator<List<Object>>() {
			public int compare(List<Object> listOne, List<Object> listTwo) {
				if ((Long) listOne.get(4) < (Long) listTwo.get(4)) {
					return 1;
				} else {
					return -1;
				}
			}
		});
	}
}
