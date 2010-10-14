package index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe permettant de gérer une liste de stopword
 * @author Brizai Olivier & Maxime Thoraval
 *
 */
public class StopWordManager {
	//Tableau des stopword
	ArrayList<String> _stopWord;
	
	/**
	 * Constructeur par défaut
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
	 * Test si un mot est présent dans la liste des stopword
	 * @param mot Le mot dont il faut vérifier la présence
	 * @return vrai s'il est présent, faux sinon
	 */
	public boolean contains(String mot){
		return _stopWord.contains(mot);
	}
	
}
