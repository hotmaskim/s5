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
		} else 

			if(request.getPathInfo().equals("/edit")){
				int id = Integer.parseInt(request.getParameter("id"));
				if(id >0) {
					Eleve eleve = service.getOne(id);
					request.setAttribute("eleve", eleve);
				}

				getServletContext().getRequestDispatcher(urlEdit).forward(request, response);

				return;
			} else 
				if(request.getPathInfo().equals("/delete")){
					int id = Integer.parseInt(request.getParameter("id"));
					service.deleteOne(id);		
					//getServletContext().getRequestDispatcher(urlList).forward(request, response);
					response.sendRedirect(getServletContext().getContextPath()+"/do/list");
					return;
				} else {
					getServletContext().getRequestDispatcher("/WEB-INF/vues/notfound.jsp").forward(request, response);
					return;
				}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(request.getPathInfo().equals("/validate")){
			int id = Integer.parseInt(request.getParameter("id"));
			int version = Integer.parseInt(request.getParameter("version"));
			String prenom = request.getParameter("prenom");
			String nom = request.getParameter("nom");
			String dateNaissance = request.getParameter("dateNaissance");
			boolean redoublant = Boolean.parseBoolean(request.getParameter("redoublant"));
			String filiere = request.getParameter("filiere");

			int annee = 0;
			try{
				annee = Integer.parseInt(request.getParameter("annee"));
			} catch (NumberFormatException nfe){
				annee= 0;
			}

			Date d = null;
			try {
				d = new SimpleDateFormat("dd/MM/yyyy").parse(dateNaissance);
			} catch (ParseException e) {
				d = new Date(0);
			}
			Eleve eleve = new Eleve(id, version, nom, prenom, d, redoublant, annee, filiere);


			try {
				service.saveOne(eleve);
				request.setAttribute("eleve", eleve);

			} catch (DAOException exc) {
				String[] erreurs = new String[6];
				erreurs[0] = exc.getMessage();
				if(exc.getSousCodes().size() > 0){
					for (Integer sc : exc.getSousCodes()) {
						switch (sc.intValue()) {
						case 1:
							erreurs[1] = "Impossibilité de création de l'objet Eleve : contactez l'administrateur";
							break;
						case 2:
							erreurs[3] = "Le nom ne peut être vide";
							break;
						case 3:
							erreurs[2] = "Le prénom ne peut être vide";
							break;
						case 4:
							erreurs[6] = "Problème dans le choix de la filière";
							break;
						case 5:
							erreurs[4] = "Date incorrecte / Non comprise entre 1980 et 1990";
							break;
						case 6:
							erreurs[5] = "Année incorrecte / Doit être 1, 2 ou 3";
							break;

						default:
							break;
						}
					}
				} else {
					request.setAttribute("exc", exc);
					getServletContext().getRequestDispatcher(urlErreurs).forward(request,response);
					return;
				}

				request.setAttribute("erreurs", erreurs);
				request.setAttribute("eleve", eleve);
				getServletContext().getRequestDispatcher(urlEdit + "?id=" + eleve.getId()).forward(request,response);
				return;

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
