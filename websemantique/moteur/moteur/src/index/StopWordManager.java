package index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StopWordManager {

	ArrayList<String> _stopWord;
	
	public StopWordManager(String fichier) throws IOException{
		_stopWord = new ArrayList<String>(); 

		BufferedReader file = new BufferedReader(new FileReader(new File(fichier)));
		String ligne;
		while((ligne = file.readLine()) != null){
			_stopWord.add(ligne);
		}
	}
	
	public boolean contains(String mot){
		return _stopWord.contains(mot);
	}
	
}
