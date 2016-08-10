package com.dinkq.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dinkq.entity.Order;

public class BotUtils {

	private static HashMap<String, BotUserState> botUserStateMap = new HashMap<String, BotUserState>();

	public static GupshupObject parseMessage(HttpServletRequest request) throws JSONException {
		String messageObj = request.getParameter("messageobj");
		String senderObj = request.getParameter("senderobj");

		JSONObject messageObjeObject = new JSONObject(messageObj);
		JSONObject senderObjeObject = new JSONObject(senderObj);
		String messageType = messageObjeObject.getString("type");
		String userMessage = messageObjeObject.getString("text");
		String channelType = senderObjeObject.getString("channeltype");
		String channelid = null;
		if (channelType.equalsIgnoreCase("telegram")) {
			long id = senderObjeObject.getLong("channelid");
			channelid = String.valueOf(id);
		} else {
			channelid = senderObjeObject.getString("channelid");
		}

		GupshupObject gs = new GupshupObject();
		try {
			gs.setChannelId(channelid);
			gs.setChannelType(channelType);
			gs.setMessageType(messageType);
			gs.setUserMessage(userMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return gs;
	}

	public static BotUserState getBotUserState(String uid) {
		return botUserStateMap.get(uid);
	}

	public static boolean setBotUserState(String uid, BotUserState bus) {
		botUserStateMap.put(uid, bus);
		return true;
	}

	public static List getStateList() {
		List<String> states = new ArrayList<String>();

		states.add("level1");
		states.add("level2");
		states.add("level3");
		return states;

	}

	public static JSONObject quickReplyTest(String message, String[] options, String msgid) {
		// String[] str = { "Red", "Green", "Yellow", "Blue" };
		List arrOptions = Arrays.asList(options);
		JSONObject my = new JSONObject();
		my.put("type", "quick_reply").put("content", new JSONObject().put("type", "text").put("text", message))
				.put("options", arrOptions).put("msgid", msgid);

		return my;

	}

	public static JSONObject coralView(List<Order> orderList, String serverPath) {

		/*
		 * { "type": "catalogue", "msgid": "cat_212", "items": [{ "title":
		 * "White T Shirt", "subtitle":
		 * "Soft cotton t-shirt \nXs, S, M, L \n$10", "imgurl":
		 * "http://petersapparel.parseapp.com/img/item100-thumb.png", "options":
		 * [ { "type": "url", "title": "View Details", "url":
		 * "http://petersapparel.parseapp.com/img/item100-thumb.png" }, {
		 * "type": "text", "title": "Buy" }
		 * 
		 * ] }, { "title": "Grey T Shirt", "subtitle":
		 * "Soft cotton t-shirt \nXs, S, M, L \n$12", "imgurl":
		 * "http://petersapparel.parseapp.com/img/item101-thumb.png", "options":
		 * [ { "type": "url", "title": "View Details", "url":
		 * "http://petersapparel.parseapp.com/img/item101-thumb.png" }, {
		 * "type": "text", "title": "Buy" }] }] }
		 */

		// List arrOptions = Arrays.asList(options);
		JSONObject my = new JSONObject();
		my.put("type", "catalogue").put("msgid", "coralview");
		// .put("content", new JSONObject().put("type", "text").put("text",
		// message))
		// .put("options", arrOptions).put("msgid", msgid);
		ArrayList<JSONObject> itemObject = new ArrayList<JSONObject>();
		for (Order order : orderList) {
			ArrayList<JSONObject> options = new ArrayList<JSONObject>();
			options.add(new JSONObject().put("type", "text").put("title", "Update " + order.getItem().getItemName()));
			options.add(new JSONObject().put("type", "text").put("title", "Delete " + order.getItem().getItemName()));
			options.add(new JSONObject().put("type", "text").put("title", "Confirm Order"));
			JSONObject catItem = null;
			if(order.getSize()!=null){
				catItem = new JSONObject().put("title", order.getItem().getItemName())
						.put("subtitle", "Size: "+order.getSize()+ " Quantity:" +order.getQuantity()+ " Price:" +order.getPrice()).put("options", options);					
			}
			else{
				catItem = new JSONObject().put("title", order.getItem().getItemName())
						.put("subtitle", " Quantity:" +order.getQuantity()+ " Price:" +order.getPrice()).put("options", options);				
			}
			catItem.put("imgurl", serverPath + "http://dummyimage.com/200x300&text="+order.getItem().getItemName());
			itemObject.add(catItem);

		}

		my.put("items", itemObject);

		return my;
	}

}
