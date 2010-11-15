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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ensicaen.tb.mvc.eleves.dao.DAOException;
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
			int id = Integer.parseInt(request.getParameter("id"));
			if(id >0) {
				Eleve eleve = service.getOne(id);
				request.setAttribute("eleve", eleve);
			}

			getServletContext().getRequestDispatcher(urlEdit).forward(request, response);

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
		String[] erreurs = new String[6];
		boolean containError = false;

		if(request.getPathInfo().equals("/validate")){
			int id = Integer.parseInt(request.getParameter("id"));
			int version = Integer.parseInt(request.getParameter("version"));
			String prenom = request.getParameter("prenom");
			if(prenom == null || prenom.equals("")) {
				containError = true;
				erreurs[0] = "Le prenom est obligatoire";
			}

			String nom = request.getParameter("nom");
			if(nom == null || nom.equals("")){
				containError = true;
				erreurs[1] = "Le nom est obligatoire";
			}
			
			String dateNaissance = request.getParameter("dateNaissance");
			if(dateNaissance == null || dateNaissance.equals("")){
				containError = true;
				erreurs[2] = "La date de naissance est obligatoire";
			}

			boolean redoublant = Boolean.parseBoolean(request.getParameter("redoublant"));

			int annee = 0;
			try{
				annee = Integer.parseInt(request.getParameter("annee"));
			} catch (NumberFormatException nfe){
				containError = true;
				erreurs[4] = "Année incorrecte";
			}
			String filiere = request.getParameter("filiere");

			Date d = null;
			try {
				d = new SimpleDateFormat("dd/MM/yyyy").parse(dateNaissance);
			} catch (ParseException e) {
				containError = true;
				erreurs[2] = "Date incorrecte";
			}
			Eleve eleve = new Eleve(id, version, nom, prenom, d, redoublant, annee, filiere);

			
			if(containError) {
				request.setAttribute("erreurs", erreurs);
				request.setAttribute("eleve", eleve);
				getServletContext().getRequestDispatcher(urlEdit + "?id=" + eleve.getId()).forward(request,response);
				return;
			}
			try {
				service.saveOne(eleve);
			} catch (DAOException exc) {
				System.out.println(exc.getMessage());
			}
		}

		response.sendRedirect("list");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

}
