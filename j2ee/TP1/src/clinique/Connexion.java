package clinique;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet qui va servir à vérifier les données de connexion ainsi qu'à se donnecter
 */
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
     GestionBDD bdd;

    
	public void init( ServletConfig config ) throws ServletException
	{
		super.init( config );

		bdd = new GestionBDD();
		
	}
	
    public Connexion() {
        super();
    }

    /**
     * Test le login et mot de passe de l'utilisateur
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if(bdd.testConnexion(request.getParameter("login"), request.getParameter("password"))){
				request.getSession().setAttribute("log", "true");
				response.sendRedirect("/TP1/Accueil");
			} else 
				response.sendRedirect("connexion.html");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	/**
	 * Appelé seulement lors du clique sur "Deconnexion" => suppression de la session
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		req.getSession().removeAttribute("log");
		res.sendRedirect("connexion.html");
	}


	public void destroy()  // Garantir que la connexion est bien ferm?e avant la fin du servlet
	{
		try { 
			bdd.close(); 
		}
		catch( Exception e ) { System.err.println( "Problème de fermeture de la BD" );}
	}

}
