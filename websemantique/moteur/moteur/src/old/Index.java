package old;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

public class Index {
	private HashMap<String, HashMap<String, Double>> _index;
	private int _nbDoc;
	
	public Index(int nbDoc) {
		_nbDoc = nbDoc;
		_index = new HashMap<String, HashMap<String, Double>>();
	}
	
	public void ajoutMot(String mot, String doc, Integer occurenceMotPourDoc){
		if(_index.get(mot) == null) {
			_index.put(mot, new HashMap<String, Double>() );
		}
		_index.get(mot).put(doc, occurenceMotPourDoc.doubleValue());
	}
	
	
	public void calculPoids(){
		//Parcours de tous les mots
		for(Iterator<String> iteMots = _index.keySet().iterator(); iteMots.hasNext(); ){
			String mot = iteMots.next();
			//Parcours de tous les documents pour ce mot
			for(Iterator<String> iteDoc = _index.get(mot).keySet().iterator(); iteDoc.hasNext(); ){
				String document = iteDoc.next();
				Double occurenceMot = _index.get(mot).get(document);
				//Calcul du poids
				_index.get(mot).put(document, new Double(occurenceMot* Math.log(_nbDoc / _index.get(mot).size())));
			}
		}
	}

	@Override
	public String toString() {
		String retour = new String("-- Affichage de l'index --\n");
		for(Iterator<String> iteMots = _index.keySet().iterator(); iteMots.hasNext(); ){
			String mot = iteMots.next();
			retour+="- " + mot + "\n";
			//Parcours de tous les documents pour ce mot
			for(Iterator<String> iteDoc = _index.get(mot).keySet().iterator(); iteDoc.hasNext(); ){
				String document = iteDoc.next();
				retour+= "\t * "+document + " ==> " + _index.get(mot).get(document) + "\n";
			}
		}
		return retour;
	}	
}
