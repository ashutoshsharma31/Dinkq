package com.dinkq.data.conf;

public class ItemPrice {
	public String name;
	public int price;
	public String size;

	public ItemPrice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemPrice(String name, int price, String size) {
		this.name = name;
		this.price = price;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "ItemPrice [name=" + name + ", price=" + price + ", size=" + size + "]";
	}

}
