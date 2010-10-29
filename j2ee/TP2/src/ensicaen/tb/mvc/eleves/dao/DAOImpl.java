/**
 * ENSICAEN
 * 6 Boulevard Marechal Juin 
 * F-14050 Caen Cedex 
 * 
 * This file is owned by ENSICAEN students.
 * No portion of this document may be reproduced, copied
 * or revised without written permission of the authors.
 */ 

/**
 * @author Olivier Brizai <olivier.brizai@ecole.ensicaen.fr>
 * @author Maxime Thoraval <maxime.thoraval@ecole.ensicaen.fr>
 * 
 * @file DAOImpl.java
 * @brief Classe de la couche DAO, gère les fonctions élémentaires sur la base de données
 */

package ensicaen.tb.mvc.eleves.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import ensicaen.tb.mvc.eleves.entities.Eleve;

public class DAOImpl implements IDAO  {

	private Connection cnx = null;	
	private ResultSet curs;
	private PreparedStatement st1, st2, st3, st4, st5; /* Pour les ordres SQL paramétrés select, insert, update, et delete (st5 = récupe version) */

	private static String GET_ALL_ELEVES = "SELECT * FROM ELEVES";
	private static String GET_ONE_ELEVE = "SELECT * FROM ELEVES WHERE ID = ?";
	private static String SAVE_ONE_ELEVE = "INSERT INTO ELEVES values (NEXTVAL('SEQ_ELEVES'), 1, ?, ?, ?, ?, ?, ?)";
	private static String UPDATE_ONE_ELEVE = "UPDATE ELEVES SET version = ?, nom = ?, prenom = ?, dateNaissance = ?, annee = ?, redoublant = ?, filiere = ? WHERE id = ?";
	private static String DELETE_ONE_ELEVE = "DELETE FROM ELEVES WHERE id = ?";
	private static String GET_CURRENT_SEQVAL = "SELECT CURRVAL('SEQ_ELEVES')";
	private static String GET_VERSION_ELEVE = "SELECT version FROM eleves WHERE id = ?";

	public DAOImpl() {
	}

	public void init() {
		String url ="jdbc:postgresql://postgres.ecole.ensicaen.fr/clinique?user=thoraval&password=canari" ;

		try {
			try {
				Class.forName("org.postgresql.Driver").newInstance();
			} catch (Exception e) {
				throw new DAOException("Impossible de charger le driver\n" + e.getMessage(), 10);
			}	

			try {
				cnx = DriverManager.getConnection (url);
			} catch(SQLException ex) {
				throw new DAOException("Problème lors de la récupération du driver\n" + ex.getMessage(), 11);
			}

			try {
				st1 = cnx.prepareStatement(GET_ONE_ELEVE);
				st2 = cnx.prepareStatement(SAVE_ONE_ELEVE);
				st3 = cnx.prepareStatement(UPDATE_ONE_ELEVE);
				st4 = cnx.prepareStatement(DELETE_ONE_ELEVE);
				st5 = cnx.prepareStatement(GET_VERSION_ELEVE);
			} catch (SQLException ex){
				throw new DAOException("Problème lors de la préparation des statements\n" + ex.getMessage(), 12);
			}
			System.out.println("Connexion OK!!");

		} catch(Exception e) {
			System.exit(1);
		}
	}

	public void destroy() {
		try {
			cnx.close();
		} catch (SQLException e) {
			throw new DAOException("Problème lors de la fermeture de la connexion\n" + e.getMessage(), 13);
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
			throw new DAOException("Récupération de la totalité des élèves impossible\n" + e.getMessage(), 20);
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
			} else
				throw new DAOException("Récupération impossible pour l'id " + id + "\n", 31);

		} catch (SQLException e) {
			throw new DAOException("Récupération impossible pour l'id " + id + "\n" + e.getMessage(), 30);
		}
	}

	@Override
	public void saveOne(Eleve e) {
		if(!testChamps(e)) {
			throw new DAOException("Update/Save Impossible, problème dans les informations de l'élève", 40);
		} else {
			if(e.getId() == -1) {
				try {
					//Save
					st2.setString(1, e.getNom());
					st2.setString(2, e.getPrenom());
					st2.setDate(3, new java.sql.Date(e.getDateNaissance().getTime())); //Conversion date
					st2.setBoolean(4, e.isRedoublant());
					st2.setInt(5, e.getAnnee());
					st2.setString(6, e.getFiliere());

					st2.executeUpdate();

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
					throw new DAOException("Save impossible \n" + ex.getMessage(), 41);
				}
			} else {
				try {
					Eleve tmp = getOne(e.getId());
					if(tmp.getVersion() > e.getVersion())
						throw new DAOException("Update impossible, Version de l'élève invalide", 43);
					//Update
					e.setVersion(e.getVersion() + 1);
					st3.setInt(1, e.getVersion());
					st3.setString(2, e.getNom());
					st3.setString(3, e.getPrenom());
					st3.setDate(4, new java.sql.Date(e.getDateNaissance().getTime()));
					st3.setInt(5, e.getAnnee());
					st3.setBoolean(6, e.isRedoublant());
					st3.setString(7, e.getFiliere());
					st3.setInt(8, e.getId());

					st3.executeUpdate();

				} catch (SQLException ex) {
					throw new DAOException("Update impossible \n" + ex.getMessage(), 42);
				}
			}
		}
	}

	private boolean testChamps(Eleve e){
		if(e == null)
			return false;

		if(e.getNom() == null || e.getNom().equals(""))
			return false;

		if(e.getPrenom() == null || e.getNom().equals(""))
			return false;

		if(e.getFiliere() == null || e.getFiliere().equals("") || (!e.getFiliere().equals("INFO")
				&& !e.getFiliere().equals("MCF") && !e.getFiliere().equals("ELEC")))
			return false;

		if(e.getDateNaissance().after(new Date(90,1,1)) || e.getDateNaissance().before(new Date(80,12,31))
				|| e.getDateNaissance().getMonth() <= 0 || e.getDateNaissance().getMonth() > 12 
				|| e.getDateNaissance().getDay() <= 0 || e.getDateNaissance().getMonth() > 31)
			return false;

		return true;
	}

	@Override
	public void deleteOne(int id) {
		try {
			if(id > 0){
				st4.setInt(1, id);

				st4.executeUpdate();
			}
		} catch (SQLException ex) {
			throw new DAOException("Delete impossible \n" + ex.getMessage(), 50);
		}
	}

}
