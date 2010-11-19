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
 * @file IDAO.java
 * @brief Interface de la couche DAO, faisant le lien entre le DAO et la couche des services
 */

package ensicaen.tb.mvc.eleves.dao;

import java.util.Collection;

import ensicaen.tb.mvc.eleves.entities.Eleve;

public interface IDAO {

	/**
	* Récupération de la liste des élèves enregistrés dans la bdd
	* @return une liste des élèves (Classe Eleve)
	*/	

	Collection<Eleve> getAll();

	/**
	* Récupération d'un élève (Classe Eleve) dans la bdd à partir de son identifiant
	* @param l'identifiant (unique) de l'élève
	* @return L'élève correspondant à l'identifiant si l'identifiant existe
	*/

	Eleve getOne(int id);

	/**
	* Sauvegarde d'un élève  dans la bdd
	* @param L'Eleve (Classe Eleve) à sauvegarder
	*/

	void saveOne(Eleve e);

	/**
	* Suppression d'un élève de la base de données
	* @param L'identifiant de l'élève qu'on souhaite supprimer
	*/

	void deleteOne(int id);
	
}
