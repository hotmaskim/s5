package clinique;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

/**
 * Servlet qui va afficher la liste des animaux dans laquelle il faudrat choisir celui à supprimer
 * @author thoraval
 *
 */
public class Choix_animal extends HttpServlet 
{
	private GestionBDD bdd;


    /**
     * Initialisation du gestionnaire de base de données
     */
	public void init( ServletConfig config ) throws ServletException
	{
		super.init( config );

		bdd = new GestionBDD();

	}

	/**
	 * Affichage de la liste des animaux dont le nom contient les informations rentrées dans le formulaire précédent
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{		
		//Récupération du nom de l'animal entré au clavier
		String nomAnimal= req.getParameter("nomAnimal");
		// Si le nom de l'animal est nul, on redigire vers le formulaire
		if(nomAnimal == null || nomAnimal == "")
			res.sendRedirect("/TP1");
		else {
			//Test si l'utilisateur est bien authentifié
			if(req.getSession().getAttribute("log")== null || !req.getSession().getAttribute("log").equals("true"))
				res.sendRedirect("connexion.html");
			else {
				res.setContentType("text/html");
				PrintWriter out = res.getWriter();

				out.println("<HTML>");
				out.println("<HEAD><TITLE>Choisir l'animal à supprimer</TITLE></HEAD>");
				out.println("<BODY>");
				out.println("<a href='/TP1/Connexion'>Deconnexion</a>");
				out.println("<Center><FORM methode='get' action='Supprime_animal' style=\"margin-top:25%;\">");

				//Récupération et affichage de la liste des animaux
				try {
					bdd.recupListAnimaux(nomAnimal.toLowerCase().substring(0, 9)); //récupère que les 10 premiers caractères (=taille du champs)
					out.println( bdd.creerListeAnimaux()); 
					out.println("<input type='submit' value='Supprimer'>");
				}

				catch (SQLException esgbd)
				{
					out.println("<BR>erreur sgbd :"+esgbd.getMessage()+"<BR>");
				}
				out.println("</FORM>");
				out.println("</CENTER></BODY>");
				out.println("</HTML>");
				out.close();
			}
		}
	}	

	public void destroy()  // Garantir que la connexion est bien ferm?e avant la fin du servlet
	{
		try { 
			bdd.close(); 
		}
		catch( Exception e ) { System.err.println( "Problème de fermeture de la BD" );}
	}

}
