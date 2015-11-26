package com.m2i.poec.javdw;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class HelloServlet
 */
// scanné par le container de servlet lorsque les classes sont déployées
// cette classe recevra la requete et la reponse passees par le container
@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 3 on veut ajouter un Hello "name" selon un parametre passé dans l'URL
		String name = request.getParameter("name");
		/// should check name is clean (no html, xml,...
		if (name == null) {
			// should throw an exception here
			return;
		}
			
		// on veut modifier l'entête pour envoyer du html à l aplace du texte
		response.setContentType("text/html");
		// on aurait aussi pu utiliser setHeader("Content-Type","html/text")
		HttpSession session  = request.getSession();
	//	String userId = (String) session.getAttribute("username");
	//	userId != ((Boolean) null) ? userId : "Anonymous";
		String userName = Objects.toString(session.getAttribute("USERNAME"), "Anonymous");
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.getWriter().append("<!DOCTYPE html>")
		                    .append("<html>")
		                    .append("<meta charset=\"ISO-8859-1\">")
		                    .append("<head>")
		                    .append("<title>Hello</title>")
		                    .append("</head>")
		                    .append("<body>")
		                    .append("<p>HELLO ").append(userName).append("<p>")
		                    //.append("<p>HELLO " + name + "</p>")
		                    .append("</body>")
		                    .append("</html>");
		                    		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
