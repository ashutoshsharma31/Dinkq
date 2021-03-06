package com.dinkq.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.dinkq.utils.BotUtils;
import com.dinkq.utils.GupshupObject;
import com.dinkq.data.conf.MenuItems;
import com.dinkq.data.conf.Quantity;
import com.dinkq.data.conf.StatusMessages;
import com.dinkq.data.objects.SessionData;
import com.dinkq.data.objects.Storage;
import com.dinkq.entity.Item;
import com.dinkq.entity.Order;

/**
 * Servlet implementation class CoffeeWeb
 */
@WebServlet("/CoffeeWeb")
public class CoffeeWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(CoffeeWeb.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CoffeeWeb() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String serverPath = request.getRequestURL().substring(0,
				request.getRequestURL().length() - request.getServletPath().length());
		try {
			GupshupObject go = BotUtils.parseMessage(request);

			String msgid = "mes_2_final";
			String usermessage = go.getUserMessage();

			PrintWriter writer = response.getWriter();
			String orderStatus = StatusMessages.START;
			String itemStatus = StatusMessages.START;

			SessionData sessionData = Storage.getObject(go.getChannelId());

			if (sessionData != null) {
				orderStatus = sessionData.getOrderStatus();
				itemStatus = sessionData.getItemStatus();
			} else {
				sessionData = new SessionData();
				sessionData.setOrderStatus(orderStatus);
				sessionData.setItemStatus(itemStatus);
				Storage.addElementToMap(go.getChannelId(), sessionData);
			}
			System.out.println(sessionData);
			// First Message or Get Started or Intro
			if (usermessage.toLowerCase().equals("hi") || usermessage.toLowerCase().equals("startchattingevent")) {

				String message = "What would you like to order ?";
				String[] options = MenuItems.getMenuItems("First");
				sessionData.setOrderStatus(StatusMessages.ORDER_START);
				sessionData.setItemStatus(StatusMessages.ITEM_START);
				writer.println(BotUtils.quickReplyTest(message, options, msgid));

			} else if (usermessage.trim().contains("Update")) {
				String itemName = usermessage.substring(7, usermessage.length() - 2);
				String[] options = Quantity.getQuantity();
				String message = "Please specify new quantity of " + itemName;
				sessionData.setItemStatus(StatusMessages.ITEM_NEW_QUANTITY);
				Item item = new Item(itemName, 5);
				sessionData.setItem(item);
				System.out.println(item + "  added as current item !");
				writer.println(BotUtils.quickReplyTest(message, options, msgid));
			} else if (usermessage.trim().contains("Delete")) {
				String itemName = usermessage.substring(7);
				int index = Integer.parseInt(usermessage.substring(usermessage.length()-1))-1;
				sessionData.removeOrderFromListUsingIndex(index);
				List<Order> orderList = sessionData.getOrderList();
				String message = "";
				String[] options = MenuItems.getMenuItems("Review Order");
				JSONObject coralObject = BotUtils.coralView(orderList, serverPath);
				System.out.println("Coral Object" + coralObject);
				writer.println(coralObject);

			} else if (sessionData.getItemStatus().equals(StatusMessages.ITEM_SINGLE_SIZE)
					&& (usermessage.equals("Small") || usermessage.equals("Medium") || usermessage.equals("Large"))) {
				Item item = sessionData.getItem();
				Order order = new Order();
				order.setItem(item);
				order.setQuantity(1);
				order.setSize(usermessage);
				order.setPrice(MenuItems.getPriceOfItem(item.getItemName(), usermessage));
				sessionData.addOrderToList(order);
				String message = "Item added ..  Whats else ?";
				String[] options = MenuItems.getMenuItems("Review Order");

				sessionData.setItemStatus(StatusMessages.ITEM_COMPLETE);
				sessionData.setOrderStatus(StatusMessages.ORDER_IN_PROGRESS);
				writer.println(BotUtils.quickReplyTest(message, options, msgid));
			}
			else if (sessionData.getItemStatus().equals(StatusMessages.ITEM_QUANTITY) && !MenuItems.isSizeable(sessionData.getItem().getItemName())) {
				Item item = sessionData.getItem();
				Order order = new Order();
				order.setItem(item);
				sessionData.setRemQuantity(sessionData.getRemQuantity()-Integer.parseInt(usermessage.trim()));
				order.setQuantity(Integer.parseInt(usermessage.trim()));					
				order.setPrice(MenuItems.getPriceOfItem(item.getItemName(), usermessage));
				sessionData.addOrderToList(order);
				String message = "Item added ..  Whats else ?";
				String[] options = MenuItems.getMenuItems("Review Order");

				sessionData.setItemStatus(StatusMessages.ITEM_COMPLETE);
				sessionData.setOrderStatus(StatusMessages.ORDER_IN_PROGRESS);
				writer.println(BotUtils.quickReplyTest(message, options, msgid));
			}
			else if (sessionData.getItemStatus().equals(StatusMessages.ITEM_QUANTITY)) {
				sessionData.setRemQuantity(Integer.parseInt(usermessage.trim()));
				if (Integer.parseInt(usermessage.toLowerCase().trim()) == 1) {
					String message = "What size do you want that? ";
					String[] options = MenuItems.getMenuItems("Size");
					sessionData.setItemStatus(StatusMessages.ITEM_SINGLE_SIZE);
					sessionData.setOrderStatus(StatusMessages.ORDER_IN_PROGRESS);
					writer.println(BotUtils.quickReplyTest(message, options, msgid));
				} else {
					String message = "How many small quantity ? ";
					String[] options = Quantity.getQuantityFromZero(sessionData.getRemQuantity());
					System.out.println(" LOGS :: options "+Arrays.toString(options));

					sessionData.setItemStatus(StatusMessages.ITEM_SIZE_SMALL);
					sessionData.setOrderStatus(StatusMessages.ORDER_IN_PROGRESS);
					writer.println(BotUtils.quickReplyTest(message, options, msgid));
				}

			} else if (sessionData.getItemStatus().equals(StatusMessages.ITEM_SIZE_SMALL)) {
				if (Integer.parseInt(usermessage.toLowerCase().trim()) != 0) {
					Item item = sessionData.getItem();
					Order order = new Order();
					order.setItem(item);
					sessionData.setRemQuantity(sessionData.getRemQuantity()-Integer.parseInt(usermessage.trim()));
					order.setQuantity(Integer.parseInt(usermessage.trim()));					
					order.setSize("Small");
					order.setPrice(MenuItems.getPriceOfItem(item.getItemName(), "Small"));
					sessionData.addOrderToList(order);
				}
				if(sessionData.getRemQuantity()!=0){
					String message = "How many medium quantity ? ";
					String[] options = Quantity.getQuantityFromZero(sessionData.getRemQuantity());
					sessionData.setItemStatus(StatusMessages.ITEM_SIZE_MEDIUM);
					sessionData.setOrderStatus(StatusMessages.ORDER_IN_PROGRESS);
					writer.println(BotUtils.quickReplyTest(message, options, msgid));
				}
				else{
					String message = "Item added ..  Whats else ?";
					String[] options = MenuItems.getMenuItems("Review Order");
					sessionData.setItemStatus(StatusMessages.ITEM_COMPLETE);
					sessionData.setOrderStatus(StatusMessages.ORDER_IN_PROGRESS);
					writer.println(BotUtils.quickReplyTest(message, options, msgid));
				}

			} else if (sessionData.getItemStatus().equals(StatusMessages.ITEM_SIZE_MEDIUM)) {
				if (Integer.parseInt(usermessage.toLowerCase().trim()) != 0) {
					Item item = sessionData.getItem();
					Order order = new Order();
					order.setItem(item);
					sessionData.setRemQuantity(sessionData.getRemQuantity()-Integer.parseInt(usermessage.trim()));
					order.setQuantity(Integer.parseInt(usermessage.trim()));					
					order.setSize("Medium");
					order.setPrice(MenuItems.getPriceOfItem(item.getItemName(), "Medium"));
					sessionData.addOrderToList(order);
				}
				if(sessionData.getRemQuantity()!=0){
					String message = "How many large quantity ? ";
					String[] options = Quantity.getQuantityFromZero(sessionData.getRemQuantity());
					sessionData.setItemStatus(StatusMessages.ITEM_SIZE_LARGE);
					sessionData.setOrderStatus(StatusMessages.ORDER_IN_PROGRESS);
					writer.println(BotUtils.quickReplyTest(message, options, msgid));
				}
				else{
					String message = "Item added ..  Whats else ?";
					String[] options = MenuItems.getMenuItems("Review Order");
					sessionData.setItemStatus(StatusMessages.ITEM_COMPLETE);
					sessionData.setOrderStatus(StatusMessages.ORDER_IN_PROGRESS);
					writer.println(BotUtils.quickReplyTest(message, options, msgid));
				}
			} else if (sessionData.getItemStatus().equals(StatusMessages.ITEM_SIZE_LARGE)) {
				if (Integer.parseInt(usermessage.toLowerCase().trim()) != 0) {
					Item item = sessionData.getItem();
					Order order = new Order();
					order.setItem(item);
					sessionData.setRemQuantity(sessionData.getRemQuantity()-Integer.parseInt(usermessage.trim()));
					order.setQuantity(Integer.parseInt(usermessage.trim()));
					order.setSize("Large");
					order.setPrice(MenuItems.getPriceOfItem(item.getItemName(), "Large"));
					sessionData.addOrderToList(order);
				}

				String message = "Item added ..  Whats else ?";
				String[] options = MenuItems.getMenuItems("Review Order");

				sessionData.setItemStatus(StatusMessages.ITEM_COMPLETE);
				sessionData.setOrderStatus(StatusMessages.ORDER_IN_PROGRESS);
				writer.println(BotUtils.quickReplyTest(message, options, msgid));
			} else if (usermessage.trim().contains("Confirm Order")) {

				submitOrderToMarchant();
				sessionData.clearOrderList();
				writer.println(
						"Order is submitted to marchant ! Sit back and relax we will let you know once your order is ready. :)");

			} else if (usermessage.trim().contains("Review Order")) {
				List<Order> orderList = sessionData.getOrderList();
				String message = "";
				for (Order order : orderList) {
					System.out.println(order);
					message += order;
				}
				String[] options = MenuItems.getMenuItems("Review Order");
				JSONObject coralObject = BotUtils.coralView(orderList, serverPath);
				System.out.println("Coral Object" + coralObject);
				writer.println(coralObject);

			}
			// When user selects from quick replies.
			else if (MenuItems.hasItem(usermessage)) {
				if ((usermessage.toLowerCase().equals("coffee") || usermessage.toLowerCase().equals("food"))) {
					String[] options = MenuItems.getMenuItems(usermessage);
					String message = "Which " + usermessage + " you like to order ?";
					writer.println(BotUtils.quickReplyTest(message, options, msgid));

				} else if (usermessage.toLowerCase().equals("both")) {
					String[] options = MenuItems.getMenuItems(usermessage);
					String message = "Which food or coffee you like to order ?";

					writer.println(BotUtils.quickReplyTest(message, options, msgid));
				} else {
					writer.println(sessionData.getOrderStatus() + " " + sessionData.getItemStatus() + " "
							+ "No item found! Please choose something else.");

				}
				sessionData.setItemStatus(StatusMessages.ITEM_IN_PROGRESS);
				sessionData.setOrderStatus(StatusMessages.ORDER_IN_PROGRESS);
			} else if (MenuItems.checkMenuItemToOrder(usermessage)) {
				String[] options = Quantity.getQuantity();
				String message = "Please specify quantity of " + usermessage;
				
				sessionData.setItemStatus(StatusMessages.ITEM_QUANTITY);
				sessionData.setItem(new Item(usermessage, 10));
				writer.println(BotUtils.quickReplyTest(message, options, msgid));
			} else if (isInteger(usermessage.trim())
					&& sessionData.getItemStatus().equals(StatusMessages.ITEM_NEW_QUANTITY)) {
				Item item = sessionData.getItem();
				System.out.println(item + "  fetched as current item !");
				Order order = sessionData.getOrder(item);
				System.out.println("ORDER" + order);
				order.setQuantity(Integer.parseInt(usermessage.trim()));
				String message = "Quantity of " + item.getItemName() + " changed to " + order.getQuantity() + "\n";
				String[] options = MenuItems.getMenuItems("Review Order");
				sessionData.setItemStatus(StatusMessages.ITEM_COMPLETE);
				sessionData.setOrderStatus(StatusMessages.ORDER_IN_PROGRESS);
				writer.println(BotUtils.quickReplyTest(message, options, msgid));
			}

			else if (isInteger(usermessage.trim())
					&& sessionData.getItemStatus().equals(StatusMessages.ITEM_QUANTITY)) {
				Item item = sessionData.getItem();
				Order order = new Order();
				order.setItem(item);
				order.setQuantity(Integer.parseInt(usermessage.trim()));
				sessionData.addOrderToList(order);
				String message = "Item added " + item.getItemName() + " with quantity " + order.getQuantity()
						+ "/n Whats else ?";
				String[] options = MenuItems.getMenuItems("Review Order");
				sessionData.setItemStatus(StatusMessages.ITEM_COMPLETE);
				sessionData.setOrderStatus(StatusMessages.ORDER_IN_PROGRESS);
				writer.println(BotUtils.quickReplyTest(message, options, msgid));
			}

			else {
				writer.println(sessionData+ "No action setup for this options. Try saying hi");
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			response.getWriter().append(e.getMessage());
		}
	}

	private void submitOrderToMarchant() {
		// TODO Auto-generated method stub
		System.out.println("ORDER SUBMITTED TO MARCHANT SUCCESSFULLY");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public static boolean isInteger(String str) {
		return str.matches("^[0-9]+$");
	}
}
