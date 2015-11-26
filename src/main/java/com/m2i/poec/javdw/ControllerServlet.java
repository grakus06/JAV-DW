package com.m2i.poec.javdw;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String myData ="Claude";
		request.setAttribute("myData", myData);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/view.jsp");
		// on donne la main 
		dispatcher.forward(request, response);
		// attention du code peut etre executé après le forward
		return;
		
	}



}
