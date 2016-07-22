package com.dinkq.entity;

import java.math.BigDecimal;

/**
 * This class characterizes an individual menu item We import big decimal to
 * handle the problem of arbitrarily long decimal places We write a toString()
 * method to easily return important information about the item
 * 
 * @author adityanaganath
 *
 */
public class Item {

	private String itemName;
	private int itemPrice;

	public Item(String itemName, int itemPrice) {

		this.itemName = itemName;
		this.itemPrice = itemPrice;
	}

	public String getName() {
		return itemName;
	}

	public int getCost() {
		return itemPrice;
	}

	public String itemToString() {
		String info = itemName + "\n" + itemPrice;
		return info;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Item [itemName=" + itemName + ", itemPrice=" + itemPrice + "]";
	}

}