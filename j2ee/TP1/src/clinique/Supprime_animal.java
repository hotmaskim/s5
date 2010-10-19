package clinique;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implémentant la suppression de l'animal sélectionné
 */
public class Supprime_animal extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private GestionBDD bdd;

    public Supprime_animal() {
        super();
    }
    

    /**
     * Initialisation du gestionnaire de base de données
     */
    public void init( ServletConfig config ) throws ServletException
	{
		super.init( config );

		bdd = new GestionBDD();
		
	}
	
    /**
     * Suppression de l'animal et affichage d'un message de confirmation de suppression
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Test si l'utilisateur est bien authentifié
		if(request.getSession().getAttribute("log")== null || !request.getSession().getAttribute("log").equals("true"))
		      response.sendRedirect("connexion.html");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		//Récupération du numéro de l'animal à supprimer
		String numAnimal = request.getParameter("animaux");
		try {
			//Suppression de l'animal
			bdd.supprimer(numAnimal);
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		//Affichage du message de confirmation
		out.println("<HTML>");
		out.println("<HEAD><TITLE>Animal supprimé</TITLE></HEAD>");
		out.println("<BODY>");
		out.println("<center>Animal supprimé !</center><br />");
		out.println("<a href='/TP1/Accueil'>Retour</a>");
		out.println("</BODY>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	//Fermeture de la connexion à la base de données
	public void destroy()  // Garantir que la connexion est bien ferm?e avant la fin du servlet
	{
		try { 
			bdd.close(); 
		}
		catch( Exception e ) { System.err.println( "Problème de fermeture de la BD" );}
	}

}
