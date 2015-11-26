package com.m2i.poec.javdw;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
 * Servlet implementation class ResourceServlet
 */
@WebServlet("/ResourceServlet")
public class ResourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String FIND_ALL_ARTICLES ="SELECT * FROM article";

	// on injecte une datasource crée sur le serveur de données
	// https://docs.oracle.com/javaee/7/tutorial/webapp005.htm#CHDHGJIA 6.5.4.1
	@Resource(lookup="java:jboss/datasources/BlogDS")
	private DataSource blogDS;
	
	// on créer un Logger pour sauvegardé des messages
	private static final Logger LOGGER= Logger.getLogger(ResourceServlet.class.getName());

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.log(Level.INFO, blogDS.toString());
		
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
			//String listArticles ="Claude";
			ArticleDAO dao = new ArticleDAO(conn);
			List<Article> articles = dao.findAll();
			
			request.setAttribute("articles", articles);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/ListeArticles.jsp");
			// on donne la main 
			dispatcher.forward(request, response);
			// attention du code peut etre executé après le forward
			return;
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
