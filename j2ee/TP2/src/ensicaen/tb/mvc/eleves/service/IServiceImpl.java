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
 * @file IServiceImpl.java
 * @brief Classe dans laquelle sont implémentés les services utilisables par l'utilisateur. Les services utilisables sont accessibles par l'interface IService.
 */

package ensicaen.tb.mvc.eleves.service;

import java.util.Collection;

import ensicaen.tb.mvc.eleves.dao.IDAO;
import ensicaen.tb.mvc.eleves.entities.Eleve;

public class IServiceImpl implements IService{

	IDAO dao;
	
	public IServiceImpl() {}

	public IServiceImpl(IDAO dao){
		this.dao = dao;
	}
	
	public IDAO getDAO(){
		return dao;
	}
	
	public void setDAO(IDAO dao){
		this.dao = dao;
	}

	/**
	* Suppression d'un élève de la base de données
	* @param L'identifiant de l'élève qu'on souhaite supprimer
	*/
	
	@Override
	public void deleteOne(int id) {
		dao.deleteOne(id);
	}
	
	/**
	* Récupération de la liste des élèves enregistrés dans la bdd
	* @return une liste des élèves (Classe Eleve)
	*/

	@Override
	public Collection<Eleve> getAll() {
		return dao.getAll();
	}

	/**
	* Récupération d'un élève (Classe Eleve) dans la bdd à partir de son identifiant
	* @param l'identifiant (unique) de l'élève
	* @return L'élève correspondant à l'identifiant si l'identifiant existe
	*/

	@Override
	public Eleve getOne(int id) {
		return dao.getOne(id);
	}

	/**
	* Sauvegarde d'un élève  dans la bdd
	* @param L'Eleve (Classe Eleve) à sauvegarder
	*/

	@Override
	public void saveOne(Eleve e) {
		dao.saveOne(e);
	}

}
