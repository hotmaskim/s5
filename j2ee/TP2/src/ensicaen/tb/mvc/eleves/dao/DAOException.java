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

public class DAOException extends RuntimeException {
	
	private int code;
	
	/**
	 * Constructeur d'une erreur provenant de DAO
	 * @param message Le message d'erreur
	 * @param code Le code de l'erreur
	 */
	
	public DAOException(String message, int code) {
		super(message);
		this.code = code;
	}

	/**
	 * Fonction qui retourne le code de l'erreur
	 * @return le code de l'erreur
	 */
	
	public int getCode() {
		return code;
	}

	
}
