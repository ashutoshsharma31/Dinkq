package com.dinkq.data.conf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dinkq.entity.Item;

public class MenuItems {
	private static HashMap<String, String[]> map = new HashMap<String, String[]>();
	private static List items = new ArrayList();
	private static String arr[] = { "Expresso", "Black Coffee", "Cappucino", "Cafe Latte", "Mocha Latte",
			"Grilled Morrocan Veg", "Falafel", "Barbeque Paneer", "Creamy Chicken", "Barbeque Chicken",
			"Pesto Chicken" };
	private static List sizeableItems = new ArrayList();
	private static String sizeable[] = { "Expresso", "Black Coffee", "Cappucino", "Cafe Latte", "Mocha Latte" };
	private static String nonsizeable[] = { "Grilled Morrocan Veg", "Falafel", "Barbeque Paneer", "Creamy Chicken",
			"Barbeque Chicken", "Pesto Chicken" };

	private static HashMap<String, Float> itemPriceMap = new HashMap<String, Float>();

	static {
		String[] first = { "Coffee", "Food" };
		map.put("First", first);
		String[] coffee = { "Expresso", "Black Coffee", "Cappucino", "Cafe Latte", "Mocha Latte" };
		map.put("Coffee", coffee);
		String[] food = { "Grilled Morrocan Veg", "Falafel", "Barbeque Paneer", "Creamy Chicken", "Barbeque Chicken",
				"Pesto Chicken" };
		map.put("Food", food);
		
		String[] review = { "Coffee", "Food", "Review Order", "Confirm Order" };
		map.put("Review Order", review);
		items = Arrays.asList(arr);
		String[] confirm = { "Review Order", "Confirm Order" };
		map.put("Confirm Order", confirm);
		items = Arrays.asList(arr);
		String[] size = { "Small", "Medium", "Large" };
		map.put("Size", size);
		sizeableItems = Arrays.asList(sizeable);

		for (int i = 0; i < sizeable.length; i++) {
			itemPriceMap.put(sizeable[i] + " Small", Float.valueOf((i + 1) * 1));
			itemPriceMap.put(sizeable[i] + " Medium", Float.valueOf((i + 1) * 2));
			itemPriceMap.put(sizeable[i] + " Large", Float.valueOf((i + 1) * 3));
		}
		for (int i = 0; i <  nonsizeable.length; i++) {
			itemPriceMap.put(nonsizeable[i], Float.valueOf((float) ((i + 1) * 1.5)));
		}

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

	public static float getPriceOfItem(String item, String size) {
		float price = 0;
		if(isSizeable(item)){
			price = itemPriceMap.get(item+" "+size);
		}
		else{
			price = itemPriceMap.get(item);
		}
		return price;
	}

	public static boolean checkMenuItem(String itemName) {
		boolean itemFound = false;
		if (itemPriceMap.get(itemName) != null) {
			itemFound = true;
		}
		return itemFound;
	}

	public static boolean checkMenuItemToOrder(String item) {
		return items.contains(item);
	}

	public static boolean isSizeable(String item) {
		return sizeableItems.contains(item);
	}
}
