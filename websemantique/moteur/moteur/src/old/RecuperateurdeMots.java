package old;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

public class RecuperateurdeMots {

	public RecuperateurdeMots() {}
	
	public HashMap<String, Integer> getMots(String fichier) throws IOException{
		HashMap<String, Integer> mots = new HashMap<String, Integer> ();
		
		BufferedReader file = new BufferedReader(new FileReader(new File(fichier)));
		String ligne;
		while((ligne = file.readLine()) != null){
			StringTokenizer token = new StringTokenizer(ligne," ,:.\"!?-“”");
			while(token.hasMoreTokens()){
				String mot = ((String)token.nextElement()).toLowerCase(); 
				if(mots.get(mot) != null)
					mots.put(mot, ((Integer)mots.get(mot)) + 1);
				else
					mots.put(mot, 1);
			}
		}
		
		return mots;
	}
}
