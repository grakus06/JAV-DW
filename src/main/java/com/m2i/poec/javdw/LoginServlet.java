package com.m2i.poec.javdw;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// on créer un Logger pour sauvegardé des messages
	private static final Logger LOGGER= Logger.getLogger(LoginServlet.class.getName());
	private static final String USERNAME = "USERNAME";
	private static final String PASSWORD = "PASSWORD";
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// on veut modifier l'entête pour envoyer du html à l aplace du texte
		response.setContentType("text/html");
		// on aurait aussi pu utiliser setHeader("Content-Type","html/text")
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		Map<String,String> defvalues = new HashMap<>();
		defvalues.put(USERNAME, "enter your nane");
		defvalues.put(PASSWORD,"enter password");
		
		response.getWriter().append(buildLoginForm(defvalues));
/*		response.getWriter().append("<!DOCTYPE html>")
		                    .append("<html>")
		                    .append("<meta charset=\"ISO-8859-1\">")
		                    .append("<head>")
		                    .append("<title>Login</title>")
		                    .append("</head>")
		                    .append("<body>")
		                    .append("<form action=\"LoginServlet\" method=\"POST\">")		                    
		                    .append("<p>")
		                    .append("<label for=\"username\">Username :</label>")
		                    .append("<input type=\"text\" name=\"username\" id=\"username\" placeholder=\"enter your identifier \" />")
		                    .append("<br/>")
		                    .append("<label for=\"pass\">Password :</label>")
		                    .append("<input type=\"password\" name=\"password\" id=\"pass\"placeholder=\"enter your password\" />")		                        
		                    .append("</p>")
		                    .append("<input type=\"submit\" value=\"Login\" />")               	
		                    .append("</form>")
		                    .append("</body>")
		                    .append("</html>")	;
		                    */
	}

	private static String buildLoginForm(Map<String,String> errors) {
		// on jette une exception si les erreurs sont null
		Objects.requireNonNull(errors);
		
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE html>")
	        .append("<html>")
	        .append("<meta charset=\"ISO-8859-1\">")
	        .append("<head>")
	        .append("<title>Login</title>")
	        .append("</head>")
	        .append("<body>")
	        .append("<form action=\"LoginServlet\" method=\"POST\">")		                    
	        .append("<p>");
	        
	        sb.append("<label for=\"username\">Username :</label>");
	        sb.append("<input type=\"text\" name=\"username\" id=\"username\" placeholder=\"enter your identifier \"/>");
	        if (errors.containsKey(USERNAME)) {
	        	sb.append("<span>").append(errors.get(USERNAME)).append("</span>");
	        }

	        sb.append("<br/>");
	        
	        sb.append("<label for=\"pass\">Password :</label>");
	        sb.append("<input type=\"password\" name=\"password\" id=\"pass\"placeholder=\"enter your password\"/>")	;	                        
	        if (errors.containsKey(PASSWORD)) {
	        	sb.append("<span>").append(errors.get(PASSWORD)).append("</span>");
	        }
	        sb .append("</p>")
	        .append("<input type=\"submit\" value=\"Login\" />")               	
	        .append("</form>")
	        .append("</body>")
	        .append("</html>")	;
		
		return sb.toString();
	}
	                         
	/*** @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1 verifier si le formulaire est valide
		// sinon le réafficher
		// 2) si oui rediriger vers page accueil;
		String user_id = request.getParameter("username");
		String user_pwd = request.getParameter("password");

		Map<String,String> errors = new HashMap<>();
		// on teste que les champs sont valides
		if ( (user_id == null) ||  (user_id.isEmpty() )) {
			errors.put(USERNAME, "username must not be empty");
		}

		if ((user_pwd == null) || (user_pwd.isEmpty()) ) {
			errors.put(PASSWORD, user_pwd);
		}

		if (!errors.isEmpty()) {
			response.setContentType("text/html");
			response.getWriter().append(buildLoginForm(errors));
			return;
		}
		// on log les parametres dans la console serveur
		LOGGER.log(Level.INFO,String.format("username: :%s password: %s", user_id,user_pwd));
		
		// créer une session et ajoute le cookie a la requete
		HttpSession session = request.getSession();
		
		LOGGER.log(Level.INFO,String.format("session %s",session));
		LOGGER.log(Level.INFO,String.format("session id %s",session.getId()));
		// on ajoute le userid au coockie
		session.setAttribute(USERNAME, user_id);
		
	//	doGet(request, response);
		// on envoie une réponse au client pour le forcer à aller sur la page voulue
		// il faut rediriger la page avec des codes http 301/302
		// ce n'est pas le serveur qui envoie la requete
		response.sendRedirect("index.html");
	}

}
