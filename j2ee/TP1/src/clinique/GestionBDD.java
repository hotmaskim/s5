package clinique;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe permettant de gérer les interactions avec la base de données
 * @author thoraval
 *
 */
public class GestionBDD {

	private ResultSet curs;
	private int cpt;
	private ResultSetMetaData info;
	private static final long serialVersionUID = 1L;
	private Connection c;
	
	/**
	 * Initialise la connexion avec la base de données
	 */
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
	
	/**
	 * Récupére la liste des animaux
	 * @param nomAnimal Nom de l'animal à récupérer
	 * @throws SQLException
	 */
	public void recupListAnimaux(String nomAnimal) throws SQLException{
		Statement requete = c.createStatement();
		curs = requete.executeQuery("SELECT idani, noman FROM mesanimaux WHERE noman LIKE '%"+nomAnimal+"%'");
		info = curs.getMetaData();
	}

	/**
	 * Crée la liste des animaux au format sql
	 * @return Une string contenant la liste des animaux
	 * @throws SQLException
	 */
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
	
	/**
	 * Fonction effectuant une requête de suppression
	 * @param numAnimal Numéro de l'animal à supprimer
	 * @throws SQLException
	 */
	public void supprimer(String numAnimal) throws SQLException{
		Statement requete = c.createStatement();
		if(requete.execute("DELETE FROM mesanimaux WHERE idani = '" + numAnimal + "'"))
			throw new SQLException("Retourne une liste de résultats au lieu d'un nombre de lignes misent à jour");
	}
	
	/**
	 * Test le login et mot de passe de l'utilisateur
	 * @param login
	 * @param password
	 * @return True si bien connecté / false ou rien sinon
	 * @throws SQLException
	 */
	public boolean testConnexion(String login, String password) throws SQLException{
		Statement requete = c.createStatement();
		if(requete.execute("Select * from utilisateur where login='"+login+"' and password='"+password+"'")){
			curs = requete.getResultSet();
			if(curs.next())
				return curs.getString(1).equals(login);
		} 
		return false;
	}
	
	/**
	 * Ferme la connexion avec la base de données
	 * @throws SQLException
	 */
	public void close() throws SQLException{
		c.close();
	}
}
