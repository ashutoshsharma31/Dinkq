package com.dinkq.data.conf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Quantity {

	private static List quantity = new ArrayList();
	private static String arr[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	static {

		quantity = Arrays.asList(arr);
	}
	public static String[] getQuantity() {

		return arr;

	}
	public static boolean checkMenuItemToOrder(String item) {
		return quantity.contains(item);
	}
	
	public static String[] getQuantityFromZero(int max){
		String array[] = new String[max+1];
		for (int i = 0; i <= max; i++) {
			array[i]=Integer.toString(i);
		}
		return array;
	}
}
