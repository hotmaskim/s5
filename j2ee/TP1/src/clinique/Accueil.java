package clinique;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet permettant de gérer la page d'accueil
 * Si connecté : affichage du formulaire de saisie du nom de l'animal
 * Si non : Affichage du formulaire de connexion
 */
public class Accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Accueil() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Redirige suivant que l'utilisateur soit connecté au non
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("log")== null || !request.getSession().getAttribute("log").equals("true"))
			response.sendRedirect("connexion.html");
		else
			response.sendRedirect("saisie_deb_animal.html");
			
	}

}
