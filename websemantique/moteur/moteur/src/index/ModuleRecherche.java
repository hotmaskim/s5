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
 * @file ModuleRecherche.java
 * @brief Classe gérant la recherche de mot dans un index
 */

package index;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

public class ModuleRecherche {

		Index _index;
		TreeMap<Double,String> _coeffSalton;
		String[] _documents;
	
		public ModuleRecherche(Index index, String[] documents) {
			_index = index;
			_documents = documents;
			_coeffSalton =  new TreeMap<Double,String>();
		}
		
		public void recherche(Vector<String> requete){
			
			//on recupère les coeff de Salton auprès de l'index
			_coeffSalton = _index.getSaltonCoeffs(requete);
			
			//affichage des coeffs de salton
			System.out.print("Coeffs de salton :");
			Iterator<Double> iteCoeff;
			for(iteCoeff = _coeffSalton.keySet().iterator();iteCoeff.hasNext();)
				System.out.println(iteCoeff.next().toString());
			System.out.print("\n");
			
			//affichage des resultats
			System.out.print("Classement des résultats par pertience :");
			for(iteCoeff = _coeffSalton.keySet().iterator();iteCoeff.hasNext();)
				System.out.println(_coeffSalton.get(iteCoeff.next()));
			System.out.print("\n");
			
		}
}
