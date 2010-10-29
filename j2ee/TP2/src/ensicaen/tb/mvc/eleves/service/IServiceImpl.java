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
 * @brief 
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
	
	@Override
	public void deleteOne(int id) {
		dao.deleteOne(id);
	}

	@Override
	public Collection<Eleve> getAll() {
		return dao.getAll();
	}

	@Override
	public Eleve getOne(int id) {
		return dao.getOne(id);
	}

	@Override
	public void saveOne(Eleve e) {
		dao.saveOne(e);
	}

}
