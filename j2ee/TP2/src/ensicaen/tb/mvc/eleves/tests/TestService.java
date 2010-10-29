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
 * @file TestService.java
 * @brief Class
 */

package ensicaen.tb.mvc.eleves.tests;

import java.util.ArrayList;
import java.util.Date;

import ensicaen.tb.mvc.eleves.dao.DAOException;
import ensicaen.tb.mvc.eleves.dao.DAOImpl;
import ensicaen.tb.mvc.eleves.entities.Eleve;
import ensicaen.tb.mvc.eleves.service.IServiceImpl;
import junit.framework.TestCase;

public class TestService extends TestCase{

	private DAOImpl dao;
	private IServiceImpl service;

	public TestService() {
		dao = new DAOImpl();
		dao.init();
		service.setDAO(dao);
	}

	/**
	 * Test les méthodes de la classe IServiceImpl
	 */

	public void test1(){
		//affichage de la liste des eleves
		ArrayList<Eleve> eleves = (ArrayList<Eleve>)service.getAll();
		for (Eleve eleve : eleves) {
			System.out.println(eleve.toString());
		}

		//ajout d'un eleve dans la table + 
		//recherche d'un eleve
		Eleve e = new Eleve("Dupont", "Henry", new Date(81, 10, 12), false, 1, "INFO");
		service.saveOne(e);
		Eleve e2 = service.getOne(e.getId());
		assertEquals(true, e.equals(e2));

		//affichage d'un eleve
		System.out.println(service.getOne(e.getId()).toString());

		//modifie les caracteristiques d'un eleves
		e.setNom("UpdatedDupond");
		service.saveOne(e);
		e = service.getOne(e.getId());
		assertEquals("UpdatedDupond", e.getNom());
		
		//supprime un eleve
		try{
			service.deleteOne(e.getId());
			service.getOne(e.getId());
		}catch (DAOException ex) {
			assertEquals(31, ex.getCode());
		}
	}

	public void test2(){
		//Modification d'un élève inexistant
		try {
			Eleve e = service.getOne(-1);
			e.setNom("Test");
			service.saveOne(e);
		} catch(DAOException ex){
			//Test d'impossibilité de récupération de l'élève
			assertEquals(31, ex.getCode());
		}
		
		Eleve e = new Eleve("Dupont", "Henry", new Date(81, 10, 12), false, 1, "INFO");
		//Suppression d'un élève inexistant
		try {
			service.deleteOne(e.getId());
		} catch(DAOException ex){
			assertEquals(50, ex.getCode());
		}
	}

	public void test3(){
		Eleve e = new Eleve("Dupont", "Henry", new Date(81, 10, 12), false, 1, "INFO");
		service.saveOne(e);
		
		Eleve e1 = service.getOne(e.getId());
		Eleve e2 = service.getOne(e.getId());
		
		e1.setNom("UpdatedDupont");
		service.saveOne(e1);
		
		// Problème de synchronisation lors de la mise à jour
		try{
			service.saveOne(e2);
		} catch (DAOException ex) {
			assertEquals(43, ex.getCode());
		}
		
		service.deleteOne(e1.getId());
	}

	public void test4(){
		Eleve e = new Eleve("", "Henry", new Date(81, 10, 12), false, 1, "ELEC");

		try{
			service.saveOne(e);
		} catch (DAOException ex) {
			assertEquals(40, ex.getCode());
		}
	}

}
