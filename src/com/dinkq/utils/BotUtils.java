package com.dinkq.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

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

		states.add("senthelp");
		states.add("optionsselected");
		states.add("quesmode");
		return states;

	}

	public static JSONObject quickReplyTest(String message, String[] options, String msgid) {
		/***
		 * { "type": "quick_reply", "content": { "type": "text", "text":
		 * "What's your favourite color?" }, "msgid": "qr_212", "options": [
		 * "Red", "Green", "Yellow", "Blue" ] }
		 * 
		 * 
		 */
		//String[] str = { "Red", "Green", "Yellow", "Blue" };
		List arrOptions = Arrays.asList(options);
		JSONObject my = new JSONObject();
		my.put("type", "quick_reply")
				.put("content", new JSONObject().put("type", "text").put("text", message))
				.put("options", arrOptions).put("msgid", msgid);

		return my;

	}

}
