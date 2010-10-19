package clinique;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
public class Servlet1 extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection c;
	
	public void init( ServletConfig config ) throws ServletException
		{
		super.init( config );
		
		try 
    	{
			Class.forName("org.postgresql.Driver").newInstance();
     	} 
		catch (Exception e)
    	{
			System.err.println("Impossible de charger le driver " + e.getMessage ()) ;
			System.exit (1) ;
    	}	
		String url ="jdbc:postgresql://postgres.ecole.ensicaen.fr/cinema?user=thoraval&password=canari" ;
		try
		{
			
			c = DriverManager.getConnection (url);
			System.out.println("Connexion OK!!");
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			System.exit(1);
		} 
		
		}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
  	throws ServletException, IOException
  	{
	
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		out.println("<HTML>");
		out.println("<HEAD><TITLE>Résultat de la requête</TITLE></HEAD>");
		out.println("<BODY>");
		String LaRequete= req.getParameter("req");
		out.println("<CENTER><b>Résultat de la requête <i>" + LaRequete + "</i></b>");
	
		try {
				String Req = LaRequete.toLowerCase();
				//if (Req.contains("select")) 
				//{
		   		MonCurseur cur=new MonCurseur(c,LaRequete);
				cur.affiche(out);
				//}
				//else out.println("<CENTER><b>Requête de Mise à jour Impossible !!");
        	}
    
		catch (SQLException esgbd)
		{
       		out.println("<BR>erreur sgbd :"+esgbd.getMessage()+"<BR>");
		}
       
		
		out.println("</CENTER></BODY>");
		out.println("</HTML>");
		out.close();
	}	

	public void destroy()  // Garantir que la connexion est bien ferm?e avant la fin du servlet
	{
 		try { 
					c.close(); 
				}
    catch( Exception e ) { System.err.println( "Problème de fermeture de la BD" );}
  }
		
}
