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
 * Servlet implementation class InsertArticleServlet
 */
@WebServlet("/InsertArticleServlet")
public class InsertArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String AUTHORID = "AUTHORID";
	private static final String TITLE = "TITLE";
	private static final String CONTENT = "CONTENT";
	// on créer un Logger pour sauvegarder des messages
	private static final Logger LOGGER= Logger.getLogger(InsertArticleServlet.class.getName());
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.log(Level.INFO,String.format("Servlet InsertArticleServlet called, method GET"));
		// affiche le formulaire
		// on veut modifier l'entête pour envoyer du html à la place du texte
		response.setContentType("text/html");
		// on aurait aussi pu utiliser setHeader("Content-Type","html/text")
		Map<String,String> defvalues = new HashMap<>();
		defvalues.put(AUTHORID, "enter your author ID");
		defvalues.put(TITLE,"enter title");			
		defvalues.put(CONTENT,"enter content");			
		response.getWriter().append(buildArticleForm(defvalues));
	}

	private static String buildArticleForm(Map<String,String> errors) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE html>")
	        .append("<html>")
	        .append("<meta charset=\"ISO-8859-1\">")
	        .append("<head>")
	        .append("<title>InsertArticle</title>")
	        .append("</head>")
	        .append("<body>")
	        .append("<form action=\"InsertArticleServlet\" method=\"POST\">")		                    
	        .append("<p>");
	        
	        sb.append("<label for=\"author\">author :</label>");
	        sb.append("<input type=\"text\" name=\"author\" id=\"author\" placeholder=\"enter your author identifier \"/>");
	        sb.append("<br/>");        
	        sb.append("<label for=\"title\">Title :</label>");
	        sb.append("<input type=\"text\" name=\"title\" id=\"title\"placeholder=\"enter article title\"/>")	;	                        
	        sb.append("<br/>");	        
	        sb.append("<label for=\"content\">Content :</label>");
	        sb.append("<input type=\"textarea\" name=\"content\" id=\"content\"placeholder=\"enter article content\"/>")	;	                        
	        sb .append("</p>")
	        .append("<input type=\"submit\" value=\"Add\" />")               	
	        .append("</form>")
	        .append("</body>")
	        .append("</html>")	;
		
		return sb.toString();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// traite le formulaire
		LOGGER.log(Level.INFO,String.format("Servlet InsertArticleServlet called, method POST"));
		// 1 verifier si le formulaire est valide
		// sinon le réafficher
		// 2) si oui rediriger vers page accueil;
		String author_id = request.getParameter("author");
		String title = request.getParameter("title");
		String content = request.getParameter("content");

		Map<String,String> errors = new HashMap<>();
		// on teste que les champs sont valides
		if ( (author_id == null) ||  (author_id.isEmpty() )) {
			errors.put(AUTHORID, "author ID must not be empty");
		}

		if ((content == null) || (content.isEmpty()) ) {
			errors.put(CONTENT, "content must not be empty");
		}
		if ((title == null) || (title.isEmpty()) ) {
			errors.put(CONTENT, "title must not be empty");
		}

		if (!errors.isEmpty()) {
			response.setContentType("text/html");
			response.getWriter().append(buildArticleForm(errors));
			return;
		}
		// on log les parametres dans la console serveur
		LOGGER.log(Level.INFO,String.format("author: :%s title %s content: %s", author_id,title,content));
		
		// créer une session et ajoute le cookie a la requete
		HttpSession session = request.getSession();
		
		LOGGER.log(Level.INFO,String.format("session %s",session));
		LOGGER.log(Level.INFO,String.format("session id %s",session.getId()));
		// on ajoute le author_id au coockie
	//	session.setAttribute(AUTHORID, author_id);
		
	//	doGet(request, response);
		// on envoie une réponse au client pour le forcer à aller sur la page voulue
		// il faut rediriger la page avec des codes http 301/302
		// ce n'est pas le serveur qui envoie la requete
		response.sendRedirect("index.html");
		
		doGet(request, response);
	}

}
