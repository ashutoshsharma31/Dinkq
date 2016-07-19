package com.dinkq.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dinkq.utils.BotUtils;
import com.dinkq.utils.GupshupObject;

/**
 * Servlet implementation class CoffeeWeb
 */
@WebServlet("/CoffeeWeb")
public class CoffeeWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		try {

			GupshupObject go = BotUtils.parseMessage(request);
			String usermessage = go.getUserMessage();
			
			PrintWriter writer = response.getWriter();

			if (usermessage.toLowerCase().equals("hi")) {

				String message = "What would you like to order ?";
				String[] options = { "Coffee", "Food", "Both" };
				String msgid = "mes_1";
				writer.println(BotUtils.quickReplyTest(message, options, msgid));
			} else if (usermessage.toLowerCase().equals("coffee")) {
				String message = "Which coffee you like to order ?";
				String[] options = { "Expresso", "Black Coffee", "Cappucino", "Cafe Latte", "Mocha Latte" };
				String msgid = "mes_1";
				writer.println(BotUtils.quickReplyTest(message, options, msgid));
			} else if (usermessage.toLowerCase().equals("food")) {
				String message = "What do you want in food ?";
				String[] options = { "Grilled Morrocan Veg", "Falafel", "Barbeque Paneer", "Creamy Chicken",
						"Barbeque Chicken", "Pesto Chicken" };
				String msgid = "mes_1";
				writer.println(BotUtils.quickReplyTest(message, options, msgid));
			} else {

				writer.println("No action setup for this options. Try saying hi");
			}

			writer.flush();
			writer.close();
		} catch (Exception e) {
			response.getWriter().append(e.getMessage());
		}
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

}
