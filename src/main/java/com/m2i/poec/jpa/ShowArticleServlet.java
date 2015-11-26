package com.m2i.poec.jpa;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

import com.m2i.poec.javdw.Article;
import com.m2i.poec.javdw.ArticleDAO;
import com.m2i.poec.javdw.ShowArticle;

/**
 * Servlet implementation class ShowArticleServlet
 */
@WebServlet("/ShowArticleServlet")
public class ShowArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// on créer un Logger pour sauvegardé des messages
	private static final Logger LOGGER= Logger.getLogger(ShowArticleServlet.class.getName());
	// injection of the EM
	@PersistenceUnit(unitName="BlogPU")
	private EntityManagerFactory entityManagerFactory;
	
	// Injection of the transaction (see maeven resource.xml
	@Resource
	private UserTransaction tx;

       
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
		EntityManager em = null;
		try {
			tx.begin();
			em = entityManagerFactory.createEntityManager();
			Article article = em.find(Article.class, new Long(articleId));
			request.setAttribute("article", article);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Article.jsp");
			// on donne la main 
			dispatcher.forward(request, response);
			// attention du code peut etre executé après le forward
			
			tx.commit();
		}
		catch(Exception e) {
			
		}

	}


}
