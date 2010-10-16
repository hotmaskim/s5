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
 * @file StopWordManager.java
 * @brief Classe permettant de g�rer une liste de stopword
*/

package index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StopWordManager {
	//Tableau des stopword
	ArrayList<String> _stopWord;
	
	/**
	 * Constructeur par d�faut
	 * @param fichier Fichier contenant la liste des stopword
	 * @throws IOException
	 */
	public StopWordManager(String fichier) throws IOException{
		_stopWord = new ArrayList<String>(); 

		BufferedReader file = new BufferedReader(new FileReader(new File(fichier)));
		String ligne;
		while((ligne = file.readLine()) != null){
			_stopWord.add(ligne);
		}
	}
	
	/**
	 * Test si un mot est pr�sent dans la liste des stopword
	 * @param mot Le mot dont il faut v�rifier la pr�sence
	 * @return vrai s'il est pr�sent, faux sinon
	 */
	public boolean contains(String mot){
		return _stopWord.contains(mot);
	}
	
}
