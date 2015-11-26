package com.m2i.poec.jpa;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * Servlet implementation class BlogServlet
 */
@WebServlet("/BlogServlet")
public class BlogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// injection of the EM
	@PersistenceUnit(unitName="BlogPU")
	private EntityManagerFactory entityManagerFactory;
	
	// Injection of the transaction (see maeven resource.xml
	@Resource
	private UserTransaction tx;
	// on créer un Logger pour sauvegardé des messages
	private static final Logger LOGGER= Logger.getLogger(BlogServlet.class.getName());
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EntityManager em = null;
		//EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			// creation of the EM must be inside transaction
			em = entityManagerFactory.createEntityManager();
			//  simple request to get the titles
			//Object o = em.createQuery("SELECT title FROM blog.article").;
			TypedQuery<Article>  query  = em.createQuery("SELECT a FROM Article a",Article.class);
			// hibernate a fait une proj SELECT * FROM Articles AS a
			List<Article> articles = query.getResultList();
			tx.commit();	
			
			request.setAttribute("articles", articles);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/ListeArticles.jsp");
			// on donne la main 
			dispatcher.forward(request, response);
			// attention du code peut etre executé après le forward
			

		}
		catch (Exception e) {
			// 
			LOGGER.log(Level.INFO,"error in transaction",e);
			try {
				tx.rollback();
			}
			catch (IllegalStateException | SecurityException | SystemException e1) {
				LOGGER.log(Level.INFO,"error in rollback",e);
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			if (em != null)
				em.close();
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
