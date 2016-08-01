package com.dinkq.entity;

import java.math.BigDecimal;

public class Item {

	private String itemName;
	private int itemPrice;
	private boolean sizeable;
	private String size;

	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Item(String itemName, int itemPrice) {

		this.itemName = itemName;
		this.itemPrice = itemPrice;
	}

	public Item(String itemName, int itemPrice, String size) {
		super();
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.size = size;
	}
	
	public Item(String itemName, int itemPrice, boolean sizeable, String size) {
		super();
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.sizeable = sizeable;
		this.size = size;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}

	public boolean isSizeable() {
		return sizeable;
	}

	public void setSizeable(boolean sizeable) {
		this.sizeable = sizeable;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "Item [itemName=" + itemName + ", itemPrice=" + itemPrice + ", sizeable=" + sizeable + ", size=" + size
				+ "]";
	}

}