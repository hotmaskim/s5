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
	
	Collection<Eleve> getAll();
	Eleve getOne(int id);
	void saveOne(Eleve e);
	void deleteOne(int id);
	
}
