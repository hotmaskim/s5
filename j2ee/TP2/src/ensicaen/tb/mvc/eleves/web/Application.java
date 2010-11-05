/**
 * ENSICAEN
 * 6 Boulevard Marechal Juin 
 * F-14050 Caen Cedex 
 * 
 * This file is owned by ENSICAEN students.
 * No portion of this document may be reproduced, copied
 * or revised without written permission of the authors.
 */ 

/**
 * @author Olivier Brizai <olivier.brizai@ecole.ensicaen.fr>
 * @author Maxime Thoraval <maxime.thoraval@ecole.ensicaen.fr>
 * 
 * @file Application.java
 * @brief 
 */

package ensicaen.tb.mvc.eleves.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ensicaen.tb.mvc.eleves.dao.DAOImpl;
import ensicaen.tb.mvc.eleves.entities.Eleve;
import ensicaen.tb.mvc.eleves.service.IServiceImpl;

/**
 * Servlet implementation class Application
 */
@SuppressWarnings("serial")
public class Application extends HttpServlet {
       
	IServiceImpl service;
	DAOImpl dao;
	String urlErreurs;
	String urlEdit;
	String urlList;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public Application() {
        super();
    }
	
	public void init() throws ServletException{
		
		urlErreurs = this.getInitParameter("urlErreurs");
		if(urlErreurs == null || urlErreurs.equals(""))
			throw new ServletException("Paramètre \"urlErreurs\" non indiqué dans web.xml\n");
		
		urlEdit = this.getInitParameter("urlEdit");
		if(urlEdit == null || urlEdit.equals(""))
			throw new ServletException("Paramètre \"urlEdit\" non indiqué dans web.xml\n");
		
		urlList = this.getInitParameter("urlList");
		if(urlList == null || urlList.equals(""))
			throw new ServletException("Paramètre \"urlList\" non indiqué dans web.xml\n");
		
		dao = new DAOImpl();
		dao.init();
		service = new IServiceImpl(dao);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("erreur", urlErreurs);
		if(request.getPathInfo().equals("/list")){
			ArrayList<Eleve> eleves = (ArrayList<Eleve>)service.getAll();
			request.setAttribute("eleves", eleves);
			
			getServletContext().getRequestDispatcher(urlList).forward(request, response);
			return;
		}
		
		if(request.getPathInfo().equals("/edit")){
			getServletContext().getRequestDispatcher(urlEdit).forward(request, response);
			
			return;
		}
		
		if(request.getPathInfo().equals("/validate")){
			
			return;
		}
		if(request.getPathInfo().equals("/delete")){
			int id = Integer.parseInt(request.getParameter("id"));
			service.deleteOne(id);		
			//getServletContext().getRequestDispatcher(urlList).forward(request, response);
			response.sendRedirect(getServletContext().getContextPath()+"/do/list");
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher(urlList);

	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}
	
}
