package com.dinkq.entity;

import java.util.ArrayList;

public class Order {
	private int quantity;
	private float price;
	private String size;
	private Item item;
	private float totalPrice;
	public Order() {
		super();

	}

	public Order(int quantity, Item item) {
		super();
		this.quantity = quantity;
		this.item = item;
	}

	public Order(int quantity, int price, String size, Item item) {
		super();
		this.quantity = quantity;
		this.price = price;
		this.size = size;
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "Order [quantity=" + quantity + ", price=" + price + ", size=" + size + ", item=" + item + "]";
	}

}
