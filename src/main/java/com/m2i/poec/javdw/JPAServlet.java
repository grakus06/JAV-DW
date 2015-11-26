package com.m2i.poec.javdw;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;


/**
 * Servlet implementation class JPAServlet
 */
@WebServlet("/JPAServlet")
public class JPAServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// injection of the EM
	@PersistenceUnit(unitName="BlogPU")
	private EntityManagerFactory entityManagerFactory;
	
	// Injection of the transaction (see maeven resource.xml
	@Resource
	private UserTransaction tx;
	// on créer un Logger pour sauvegardé des messages
	private static final Logger LOGGER= Logger.getLogger(JPAServlet.class.getName());

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
			// a simple request to count the number of articles
			Object o = em.createNativeQuery("SELECT COUNT(*) FROM blog.article").getSingleResult();
			LOGGER.log(Level.INFO,"The number of Articles in blog is " + o.toString());
			tx.commit();
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

}
