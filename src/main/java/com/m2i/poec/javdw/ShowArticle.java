package com.m2i.poec.javdw;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class ShowArticle
 */
@WebServlet("/ShowArticle")
public class ShowArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	// on injecte une datasource crée sur le serveur de données
	// https://docs.oracle.com/javaee/7/tutorial/webapp005.htm#CHDHGJIA 6.5.4.1
	@Resource(lookup="java:jboss/datasources/BlogDS")
	private DataSource blogDS;
	
	// on créer un Logger pour sauvegardé des messages
	private static final Logger LOGGER= Logger.getLogger(ShowArticle.class.getName());

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		LOGGER.log(Level.INFO, "showarticle");
		Integer articleId;
		
		// 3 on veut ajouter un Hello "name" selon un parametre passé dans l'URL
		try {
			String articleIdx = request.getParameter("art");
			articleId = Integer.parseInt(articleIdx);
		}
		catch (NumberFormatException e){
			// la ressource n'existe pas err 404
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
			
		}
		
		// get a connection and execute the query
	    try {
			Connection conn = blogDS.getConnection();
/*			Statement stmt = conn.createStatement();
			stmt.executeQuery(FIND_ALL_ARTICLES);
			ResultSet rs = stmt.getResultSet();		
			while (rs.next()) {
				LOGGER.log(Level.INFO, rs.getString("title"));
				}
	*/		
			ArticleDAO dao = new ArticleDAO(conn);
			Article article = dao.find(articleId);
			// todo : send 404 if id not corresponding to a valid entry in DB 
			if (article == null)
			 {
				// la ressource n'existe pas err 404
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;				
			}
			LOGGER.log(Level.INFO, article.toString());
			request.setAttribute("article", article);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Article.jsp");
			// on donne la main 
			dispatcher.forward(request, response);
			// attention du code peut etre executé après le forward
			return;
			
			} catch (SQLException e) {
				throw new ServletException(e);
			}

	}



}
