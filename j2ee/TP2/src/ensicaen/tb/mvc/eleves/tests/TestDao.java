package ensicaen.tb.mvc.eleves.tests;

import java.util.ArrayList;

import ensicaen.tb.mvc.eleves.dao.DAOException;
import ensicaen.tb.mvc.eleves.dao.DAOImpl;
import ensicaen.tb.mvc.eleves.entities.Eleve;
import junit.framework.TestCase;


public class TestDao extends TestCase{

	private DAOImpl dao;

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

			//ajout d'un eleve dans la table
	
	
			//recherche d'un eleve
			try{
				dao.getOne(2);
			}catch (DAOException e) {
				assertEquals(10, e.getCode());
			}
			
			//affichage d'un eleve

			//supprime un eleve

			//modifie les caracteristiques d'un eleves
	}

	public void test2(){

	}

	public void test3(){

	}

	public void test4(){

	}

}
