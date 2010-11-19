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
 * @file DAOException.java
 * @brief Classe représentant une erreur en provenance du DAO
 */

package ensicaen.tb.mvc.eleves.dao;

import java.util.ArrayList;

public class DAOException extends RuntimeException {
	
	private int code;
	private ArrayList<Integer> souscodes;
	
	/**
	 * Constructeur d'une erreur provenant du DAO
	 * @param message Le message d'erreur
	 * @param code Le code de l'erreur
	 */
	
	public DAOException(String message, int code) {
		super(message);
		this.code = code;
		this.souscodes = new ArrayList<Integer>();
	}

	/**
	 * Constructeur d'une erreur provenant du DAO
	 * @param message Le message d'erreur
	 * @param code Le code de l'erreur
	 * @param souscode Tableau de sous codes permettant de préciser l'origine de l'erreur
	 */
	
	public DAOException(String message, int code, ArrayList<Integer> souscodes) {
		super(message);
		this.code = code;
		this.souscodes = souscodes;
	}
	
	/**
	 * Fonction qui retourne le code de l'erreur
	 * @return le code de l'erreur
	 */
	
	public int getCode() {
		return code;
	}

	public ArrayList<Integer> getSousCodes(){
		return souscodes;
	}
	
}
