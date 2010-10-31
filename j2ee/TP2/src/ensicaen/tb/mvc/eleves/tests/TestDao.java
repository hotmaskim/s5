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
 * @file TestDao.java
 * @brief Classe dans laquelle sont faits tous les tests sur le DAO (DaoImpl) en utilisant la bibliothèque JUnit
 */

package ensicaen.tb.mvc.eleves.tests;

import java.util.ArrayList;
import java.util.Date;

import ensicaen.tb.mvc.eleves.dao.DAOException;
import ensicaen.tb.mvc.eleves.dao.DAOImpl;
import ensicaen.tb.mvc.eleves.entities.Eleve;
import junit.framework.TestCase;


public class TestDao extends TestCase{

	private DAOImpl dao;

	/**
	* Constructeur de cette classe de test
	*/

	public TestDao() {
		dao = new DAOImpl();
		dao.init();
	}

	/**
	* Test les méthodes de la classe DaoImpl
	*/

	public void test1(){
		//affichage de la liste des eleves
		ArrayList<Eleve> eleves = (ArrayList<Eleve>)dao.getAll();
		for (Eleve eleve : eleves) {
			System.out.println(eleve.toString());
		}

		//ajout d'un eleve dans la table + 
		//recherche d'un eleve
		Eleve e = new Eleve("Dupont", "Henry", new Date(81, 10, 12), false, 1, "INFO");
		dao.saveOne(e);
		Eleve e2 = dao.getOne(e.getId());
		assertEquals(true, e.equals(e2));

		//affichage d'un eleve
		System.out.println(dao.getOne(e.getId()).toString());

		//modifie les caracteristiques d'un eleves
		e.setNom("UpdatedDupond");
		dao.saveOne(e);
		e = dao.getOne(e.getId());
		assertEquals("UpdatedDupond", e.getNom());
		
		//supprime un eleve
		try{
			dao.deleteOne(e.getId());
			dao.getOne(e.getId());
		}catch (DAOException ex) {
			assertEquals(31, ex.getCode());
		}
	}

	/**
	* Teste les modification et suppressipns d'un élève inexistant
	* Modifie un élève inexistant. Supprime un élève inexistant.
	*/

	public void test2(){
		//Modification d'un élève inexistant
		try {
			Eleve e = dao.getOne(-1);
			e.setNom("Test");
			dao.saveOne(e);
		} catch(DAOException ex){
			//Test d'impossibilité de récupération de l'élève
			assertEquals(31, ex.getCode());
		}
		
		Eleve e = new Eleve("Dupont", "Henry", new Date(81, 10, 12), false, 1, "INFO");
		//Suppression d'un élève inexistant
		try {
			dao.deleteOne(e.getId());
		} catch(DAOException ex){
			assertEquals(50, ex.getCode());
		}
	}

	/**
	* Teste les problèmes de synchronisation lors de la mise à jour dans la BDD
	*/

	public void test3(){
		Eleve e = new Eleve("Dupont", "Henry", new Date(81, 10, 12), false, 1, "INFO");
		dao.saveOne(e);
		
		Eleve e1 = dao.getOne(e.getId());
		Eleve e2 = dao.getOne(e.getId());
		
		e1.setNom("UpdatedDupont");
		dao.saveOne(e1);
		
		// Problème de synchronisation lors de la mise à jour
		try{
			dao.saveOne(e2);
		} catch (DAOException ex) {
			assertEquals(43, ex.getCode());
		}
		
		dao.deleteOne(e1.getId());
	}

	/**
	* Test de la validité de la méthode saveOne()
	* On utilise implicitement ici la métode testChamps() de la classe DAOImpl pour tester tous les champs
	*/

	public void test4(){
		Eleve e = new Eleve("", "Henry", new Date(81, 10, 12), false, 1, "ELEC");

		try{
			dao.saveOne(e);
		} catch (DAOException ex) {
			assertEquals(40, ex.getCode());
		}
	}

}
