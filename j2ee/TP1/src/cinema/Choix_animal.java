package cinema;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
public class Choix_animal extends HttpServlet 
{
	private GestionBDD bdd;
	/**
	 * 
	 */

	public void init( ServletConfig config ) throws ServletException
	{
		super.init( config );

		bdd = new GestionBDD();

	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		out.println("<HTML>");
		out.println("<HEAD><TITLE>Choisir l'animal à supprimer</TITLE></HEAD>");
		out.println("<BODY>");
		out.println("<FORM methode='get' action='Supprime_animal'>");
		String nomAnimal= req.getParameter("nomAnimal");
		//out.println("<CENTER><b>Résultat de la requête <i>" + LaRequete + "</i></b>");

		try {
			String Req = nomAnimal.toLowerCase();
			bdd.recupListAnimaux("SELECT idani, noman FROM mesanimaux WHERE noman LIKE '%"+nomAnimal+"%'");
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

	public void destroy()  // Garantir que la connexion est bien ferm?e avant la fin du servlet
	{
		try { 
			bdd.close(); 
		}
		catch( Exception e ) { System.err.println( "Problème de fermeture de la BD" );}
	}

}
