package cinema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class GestionBDD {

	private ResultSet curs;
	private int cpt;
	private ResultSetMetaData info;
	private static final long serialVersionUID = 1L;
	private Connection c;
	
	public GestionBDD() {
		try 
		{
			Class.forName("org.postgresql.Driver").newInstance();
		} 
		catch (Exception e)
		{
			System.err.println("Impossible de charger le driver " + e.getMessage ()) ;
			System.exit (1) ;
		}	
		String url ="jdbc:postgresql://postgres.ecole.ensicaen.fr/clinique?user=thoraval&password=canari" ;
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
	
	public void recupListAnimaux(String req) throws SQLException{
		Statement requete = c.createStatement();
		curs = requete.executeQuery(req);
		info = curs.getMetaData();
	}

	public String creerListeAnimaux() throws SQLException{
		String listeAnimaux = "<select name='animaux'>";
		while ( curs.next() )
		{
			listeAnimaux +="<option value='"+ curs.getString(1) +"'>";
			listeAnimaux +=curs.getString(2);
			listeAnimaux +="</option>";
		}
		listeAnimaux +="</select>";
		return listeAnimaux;
	}
	
	public void supprimer(String req) throws SQLException{
		Statement requete = c.createStatement();
		curs = requete.executeQuery(req);
	}
	
	public void close() throws SQLException{
		c.close();
	}
}
