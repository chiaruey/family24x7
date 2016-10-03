package com.family.util;

import java.util.Comparator;

/**
 * This class is for sorting integer in descending order
 * 
 * @author Daddy
 *
 */
public class IntDescComparator implements Comparator<Integer> {
	
	public int compare(Integer value1, Integer value2) {
		return -1 * value1.compareTo(value2);
	}
	
}
