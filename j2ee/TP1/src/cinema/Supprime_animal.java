package cinema;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Supprime_film
 */
public class Supprime_animal extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private GestionBDD bdd;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Supprime_animal() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	public void init( ServletConfig config ) throws ServletException
	{
		super.init( config );

		bdd = new GestionBDD();
		
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String numAnimal = request.getParameter("animaux");
		try {
		bdd.supprimer("DELETE FROM mesanimaux WHERE idani = '" + numAnimal + "'");
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		out.println("<HTML>");
		out.println("<HEAD><TITLE>Animal supprimé</TITLE></HEAD>");
		out.println("<BODY>");
		out.println("Animal supprimé !");                                                                                                                                                                                                                                             
		out.println("</BODY>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}


	public void destroy()  // Garantir que la connexion est bien ferm?e avant la fin du servlet
	{
		try { 
			bdd.close(); 
		}
		catch( Exception e ) { System.err.println( "Problème de fermeture de la BD" );}
	}

}
