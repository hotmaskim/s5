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
 * @file Main.java
 * @brief Classe qui permet teste les différents modules
 */

package index;


import java.util.Vector;

public class Main {
	
	public static void main(String[] args) {
		
		try {
			
			//Test de la création d'un index à partir de fichiers
			for(int i = 0; i <args.length; i++){
				System.out.println(i + " " + args[i]);
			}
			Index index = new Index(args);
			System.out.println(index.toString());
			
			//Test du module de recherche sur l'index nouvellement créé
			ModuleRecherche md = new ModuleRecherche(index, args);
			Vector<String> mots = new Vector<String>();
			//mots.add("lapin");
			mots.add("corbeau");
			md.recherche(mots);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
