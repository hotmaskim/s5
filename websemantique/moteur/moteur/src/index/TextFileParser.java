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
 * @file RecuperateurdeMots.java
 * @brief Classe retournant une liste des mots et de leur occurence à partir d'un document
 */

package index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

public class TextFileParser {
	StopWordManager _stopWord;
	
	public TextFileParser() {
		try {
			_stopWord = new StopWordManager("../stopword.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<String, Double> getMots(String fichier) throws IOException{
		HashMap<String, Double> mots = new HashMap<String, Double> ();
		
		BufferedReader file = new BufferedReader(new FileReader(new File(fichier)));
		String ligne;
		while((ligne = file.readLine()) != null){
			StringTokenizer token = new StringTokenizer(ligne," ,;:.\"!?-“”\n\t\f\r");
			while(token.hasMoreTokens()){
				String mot = ((String)token.nextElement()).toLowerCase(); 
				if(!_stopWord.contains(mot)){
					if(mots.get(mot) != null)
						mots.put(mot, mots.get(mot) + 1);
					else
						mots.put(mot, 1.0);
				}
			}
		}
		return mots;
	}
}
