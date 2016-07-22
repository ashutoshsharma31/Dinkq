package com.dinkq.data.conf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MenuItems {
	private static HashMap<String, String[]> map = new HashMap<String, String[]>();
	private static List items = new ArrayList();
	private static String arr[] = { "Expresso", "Black Coffee", "Cappucino", "Cafe Latte", "Mocha Latte",
			"Grilled Morrocan Veg", "Falafel", "Barbeque Paneer", "Creamy Chicken", "Barbeque Chicken",
			"Pesto Chicken" };
	static {
		String[] first = { "Coffee", "Food", "Both" };
		map.put("First", first);
		String[] coffee = { "Expresso", "Black Coffee", "Cappucino", "Cafe Latte", "Mocha Latte" };
		map.put("Coffee", coffee);
		String[] food = { "Grilled Morrocan Veg", "Falafel", "Barbeque Paneer", "Creamy Chicken", "Barbeque Chicken",
				"Pesto Chicken" };
		map.put("Food", food);
		String[] both = { "Expresso", "Black Coffee", "Cappucino", "Cafe Latte", "Mocha Latte", "Grilled Morrocan Veg",
				"Falafel", "Barbeque Paneer", "Creamy Chicken", "Barbeque Chicken", "Pesto Chicken" };
		map.put("Both", both);
		String[] review = { "Coffee", "Food", "Both", "Review Order" };
		map.put("Review Order", review);
		items = Arrays.asList(arr);
		String[] confirm = { "Update Order", "Confirm Order" };
		map.put("Confirm Order", confirm);
		items = Arrays.asList(arr);
	}

	public static boolean hasItem(String key) {
		if (map.get(key) != null) {
			return true;
		} else {
			return false;
		}
	}

	public static String[] getMenuItems(String key) {

		return map.get(key);

	}

	public static void printAllMenuItems() {
		for (String name : map.keySet()) {

			String key = name.toString();
			String[] value = map.get(name);
			System.out.println(key + " " + value[0]);

		}
	}

	public static boolean checkMenuItemToOrder(String item) {
		return items.contains(item);
	}
}
