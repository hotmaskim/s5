package ensicaen.tb.mvc.eleves.tests;

import java.util.ArrayList;
import java.util.Date;


import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;


import ensicaen.tb.mvc.eleves.dao.IDAO;
import ensicaen.tb.mvc.eleves.entities.Eleve;

public class MainTestDaoPostgres {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			IDAO dao =(IDAO) (new XmlBeanFactory(new ClassPathResource("spring-config.xml"))).getBean("dao");
			
			ArrayList<Eleve> eleves= (ArrayList<Eleve>)dao.getAll();

			for (Eleve eleve : eleves) {
				System.out.println("\n-----------------------------------\n" + eleve + "\n-----------------------------------\n");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}
