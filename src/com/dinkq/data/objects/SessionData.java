package com.dinkq.data.objects;

import java.util.ArrayList;
import java.util.List;

import com.dinkq.entity.Item;
import com.dinkq.entity.Order;
import com.dinkq.entity.Person;
import com.dinkq.utils.GupshupObject;

public class SessionData {
	Person person;
	List<Order> orderList = new ArrayList<Order>();
	String orderStatus;
	String itemStatus;
	GupshupObject gupshupObject;
	Item item;
	int itemQuantity;
	int remQuantity;

	public SessionData() {
		super();
	}

	public SessionData(Person person, List<Order> orderList, String orderStatus, String itemStatus,
			GupshupObject gupshupObject, Item item) {
		super();
		this.person = person;
		this.orderList = orderList;
		this.orderStatus = orderStatus;
		this.itemStatus = itemStatus;
		this.gupshupObject = gupshupObject;
		this.item = item;
	}

	/**
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * @param person
	 *            the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	/**
	 * @return the orderList
	 */
	public List<Order> getOrderList() {
		return orderList;
	}

	/**
	 * @param orderList
	 *            the orderList to set
	 */
	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus
	 *            the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return the itemStatus
	 */
	public String getItemStatus() {
		return itemStatus;
	}

	/**
	 * @param itemStatus
	 *            the itemStatus to set
	 */
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	/**
	 * @return the gupshupObject
	 */
	public GupshupObject getGupshupObject() {
		return gupshupObject;
	}

	/**
	 * @param gupshupObject
	 *            the gupshupObject to set
	 */
	public void setGupshupObject(GupshupObject gupshupObject) {
		this.gupshupObject = gupshupObject;
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	public void addOrderToList(Order order) {
		orderList.add(order);
	}

	public Order getOrder(Item item) {
		Order result = null;
		for (Order order : orderList) {
			if (order.getItem().getItemName().equals(item.getItemName())) {
				result = order;
				break;
			}
		}
		return result;
	}

	public void removeOrderFromList(String itemName) {
		int index = 0;
		for (Order order : orderList) {
			if (order.getItem().getItemName().equals(itemName)) {
				orderList.remove(index);
			}
			index++;
		}

	}

	public void clearOrderList() {
		orderList.clear();
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public int getRemQuantity() {
		return remQuantity;
	}

	public void setRemQuantity(int remQuantity) {
		this.remQuantity = remQuantity;
	}

	@Override
	public String toString() {
		return "SessionData [person=" + person + ", orderList=" + orderList + ", orderStatus=" + orderStatus
				+ ", itemStatus=" + itemStatus + ", gupshupObject=" + gupshupObject + ", item=" + item
				+ ", itemQuantity=" + itemQuantity + "]";
	}

}
