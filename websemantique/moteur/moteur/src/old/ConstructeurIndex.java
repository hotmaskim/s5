package old;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

public class ConstructeurIndex {
	
	private Index _index;
	
	public ConstructeurIndex(String[] fichiers) throws IOException {
		System.out.println("coucou");
		_index = new Index(fichiers.length);
		RecuperateurdeMots recup = new RecuperateurdeMots();
		//Parcours des fichiers
		for(int i = 0; i<fichiers.length; i++){
			System.out.println("Fichier n°" + i);
			HashMap<String, Integer> mots = recup.getMots(fichiers[i]);

			System.out.println(mots.size());
			for(Iterator<String> iteMots = mots.keySet().iterator(); iteMots.hasNext(); ){
				System.out.println(iteMots.next());
			}
			//Parcours de la liste des mots du fichier
			for(Iterator<String> ite = mots.keySet().iterator(); ite.hasNext(); ){
				String m = ite.next();
				System.out.println("Mots = " + m);
				_index.ajoutMot(m, fichiers[i], mots.get(m));
			}
		}
		_index.calculPoids();
		System.out.println(_index.toString());
	}
	
	
	public static void main(String[] args) {
		try {
			for(int i = 0; i <args.length; i++){
				System.out.println(i + " " + args[i]);
			}
			new ConstructeurIndex(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
