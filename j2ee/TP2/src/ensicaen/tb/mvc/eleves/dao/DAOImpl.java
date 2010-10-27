package ensicaen.tb.mvc.eleves.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import ensicaen.tb.mvc.eleves.entities.Eleve;

public class DAOImpl implements IDAO  {

	private Connection cnx = null;	
	private ResultSet curs;
	private PreparedStatement st1, st2, st3, st4, st5; /* Pour les ordres SQL paramétrés select, insert, update, et delete (st5 = récupe version) */

	private static String GET_ALL_ELEVES = "SELECT * FROM ELEVES";
	private static String GET_ONE_ELEVE = "SELECT * FROM ELEVES WHERE ID = ?";
	private static String SAVE_ONE_ELEVE = "INSERT INTO ELEVES values (NEXTVAL('SEQ_ELEVES'), 1, ?, ?, ?, ?, ?, ?, ?)";
	private static String UPDATE_ONE_ELEVE = "UPDATE ELEVES SET version = ?, nom = ?, prenom = ?, dateNaissance = ?, annee = ?, redoublant = ?, filiere = ? WHERE id = ?";
	private static String DELETE_ONE_ELEVE = "DELETE FROM ELVES WHERE id = ?";
	private static String GET_CURRENT_SEQVAL = "SELECT CURRVAL('SQL_ELEVES')";
	private static String GET_VERSION_ELEVE = "SELECT version FROM eleves WHERE id = ?";


	/*
	 * 
	 * 
	 * 
	 * 
	 * MIEUX GERER LES EXCEPTIONS (Eleve non existant, ...)
	 * 
	 * 
	 * 
	 * 
	 */

	public DAOImpl() {
	}

	public void init() {
		try 
		{
			Class.forName("org.postgresql.Driver").newInstance();
		} 
		catch (Exception e)
		{
			throw new DAOException("Impossible de charger le driver\n" + e.getMessage(), 10);
		}	
		String url ="jdbc:postgresql://postgres.ecole.ensicaen.fr/clinique?user=thoraval&password=canari" ;
		try
		{

			cnx = DriverManager.getConnection (url);
			st1 = cnx.prepareStatement(GET_ONE_ELEVE);
			st2 = cnx.prepareStatement(SAVE_ONE_ELEVE);
			st3 = cnx.prepareStatement(UPDATE_ONE_ELEVE);
			st4 = cnx.prepareStatement(DELETE_ONE_ELEVE);
			st5 = cnx.prepareStatement(GET_VERSION_ELEVE);
			System.out.println("Connexion OK!!");
		}

		catch (SQLException e)
		{
			e.printStackTrace();
			System.exit(1);
		} 
	}

	public void destroy() {
		try {
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Collection<Eleve> getAll()  {
		ArrayList<Eleve> eleves = new ArrayList<Eleve>();
		try {
			Statement requete = cnx.createStatement();
			curs = requete.executeQuery(GET_ALL_ELEVES);
			while(curs.next()){
				eleves.add(new Eleve(curs.getInt(1), curs.getInt(2), curs.getString(3), 
						curs.getString(4), curs.getDate(5), curs.getBoolean(6), 
						curs.getInt(7), curs.getString(8)));
			}

		} catch (SQLException e) {
			throw new DAOException("Récupération de la totalité des élèves impossible\n" + e.getMessage(), 1);
		}
		return eleves;
	}

	@Override
	public Eleve getOne(int id) {
		try {
			st1.setInt(1, id);
			curs = st1.executeQuery();
			if(curs.next()){
				return new Eleve(curs.getInt(1), curs.getInt(2), curs.getString(3), 
						curs.getString(4), curs.getDate(5), curs.getBoolean(6), 
						curs.getInt(7), curs.getString(8));
			}
		} catch (SQLException e) {
			throw new DAOException("Récupération impossible pour l'id " + id + "\n" + e.getMessage(), 2);
		}
		return null;		
	}

	@Override
	public void saveOne(Eleve e) {
		int retour;
		if(e.getId() == -1) {
			try {
				//Save
				st2.setString(1, e.getNom());
				st2.setString(2, e.getPrenom());
				st2.setDate(3, new java.sql.Date(e.getDateNaissance().getTime()));
				st2.setBoolean(4, e.isRedoublant());
				st2.setInt(5, e.getAnnee());
				st2.setString(6, e.getFiliere());

				retour = st2.executeUpdate();

				//Mise à jour de l'objet élève
				curs = cnx.createStatement().executeQuery(GET_CURRENT_SEQVAL);
				if(curs.next()) {
					//Récupération id
					e.setId(curs.getInt(1));

					//Récupération nouvelle version
					st5.setInt(1, e.getId());
					curs = st5.executeQuery();
					if(curs.next())	
						e.setVersion(curs.getInt(1));
				}

			} catch (SQLException ex) {
				throw new DAOException("Save impossible \n" + ex.getMessage(), 3);
			}
		} else {
			try {
				//Update
				e.setVersion(e.getVersion() + 1);
				st3.setInt(1, e.getVersion());
				st3.setString(2, e.getNom());
				st3.setString(3, e.getPrenom());
				st3.setDate(4, new java.sql.Date(e.getDateNaissance().getTime()));
				st3.setBoolean(5, e.isRedoublant());
				st3.setInt(5, e.getAnnee());
				st3.setString(7, e.getFiliere());
				st3.setInt(8, e.getId());

				retour = st3.executeUpdate();

			} catch (SQLException ex) {
				throw new DAOException("Update impossible \n" + ex.getMessage(), 3);
			}
		}

	}

	@Override
	public void deleteOne(Eleve e) {
		int retour;
		try {
			if(e.getId() > 0){
				st4.setInt(1, e.getId());

				retour = st4.executeUpdate();
			}
		} catch (SQLException ex) {
			throw new DAOException("Delete impossible \n" + ex.getMessage(), 3);
		}
	}

}
