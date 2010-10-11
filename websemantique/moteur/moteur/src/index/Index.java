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
 * @file Index.java
 * @brief Classe Représentant un Index
 */

package index;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class Index {
	private HashMap<String, HashMap<String, Double>> _index;
	
	/**
	 * Constructeur par défaut
	 */
	
	public Index() {}
	
	/**
	 * @throws SAXException 
	 * Constructeur qui créé l'index à partir de fichiers
	 * @param fichiers
	 * @throws  
	 */
	
	public Index(String[] fichiers) throws IOException, SAXException {
		_index = new HashMap<String, HashMap<String, Double>>();
		ajouterAIndex(fichiers);
	}
	
	/**
	 * Fonction permettant d'ajouter des fichiers à l'index
	 * Le système d'ajout de fichier gère les documents .txt et XHTML
	 * @param fichiers
	 * @throws IOException 
	 * @throws SAXException 
	 */
		
	public void ajouterAIndex(String[] fichiers) throws IOException, SAXException{
		
		for(int i = 0; i<fichiers.length; i++){
			System.out.println("Fichier n°" + i);
			
			//detection du type de fichier
			String ext = fichiers[0].substring(fichiers[0].lastIndexOf('.')+1,fichiers[0].length());
			if(ext.compareTo("html") == 0){
				XMLReader saxReader = XMLReaderFactory.createXMLReader();
				XHTMLFileParser recup = new XHTMLFileParser();
                saxReader.setContentHandler(recup);
                saxReader.parse(fichiers[0]);
				_index.put(fichiers[i], new HashMap<String, Double>(recup.getMots(fichiers[i])));	
			}else{
				TextFileParser recup = new TextFileParser();
				_index.put(fichiers[i], new HashMap<String, Double>(recup.getMots(fichiers[i])));		
			}
		}
		calculPoids();
	}
	
	/**
	 * Fonction permettant d'ajouter mot à l'Index
	 * @param mot
	 * @param doc
	 * @param occurenceMotPourDoc
	 */
	
	/*public void ajoutMot(String mot, String doc, Integer occurenceMotPourDoc){
		if(_index.get(doc) == null) {
			_index.put(doc, new HashMap<String, Double>() );
		}
		_index.get(doc).put(mot, occurenceMotPourDoc.doubleValue());
	}*/
	
	/**
	 * Fonction qui calcule le coefficient TF IDF de chaque mot par rapport à son document
	 */
	
	private void calculPoids(){
		//Parcours de tous les documents
		for(Iterator<String> iteDoc = _index.keySet().iterator(); iteDoc.hasNext(); ){
			String doc = iteDoc.next();
			//Parcours de tous mots pour ce document
			for(Iterator<String> iteMots = _index.get(doc).keySet().iterator(); iteMots.hasNext(); ){
				String mot = iteMots.next();
				int occurenceMotTotale =0;
				for(Iterator<String> iteDoc2 = _index.keySet().iterator(); iteDoc2.hasNext();){
					if(_index.get(iteDoc2.next()).containsKey(mot)){
						occurenceMotTotale++;
					}
				}
				Double occurenceMot = _index.get(doc).get(mot);
				//Calcul du poids
				_index.get(doc).put(mot, new Double(occurenceMot* Math.log(_index.size() / occurenceMotTotale)));
			}
		}
	}
	
	/**
	 * Fonction qui calcule le coefficient de Salton pour un requete et un document donné
	 * @param requete
	 * @param document
	 */
	
	private double coeffSalton(Vector<String> requete, String document){
		HashMap<String, Double> listeMots = _index.get(document);
		//calcul d'une pondération suivant l'ordre d'apparition des mots
		double ponderation[] = new double[requete.size()];
		for(int i=0; i< requete.size(); i++){
			ponderation[i] = Math.exp(-Math.pow((i/5),2));//décroissance exponentielle lente au début puis rapide vers la fin
		}
		int size = listeMots.size();
		double v[] = new double[size];
		double r[] = new double[size];
		int i =0;
		//on rempli v et r
		for(Iterator<String> iteMots = listeMots.keySet().iterator(); iteMots.hasNext();){
			String mot = iteMots.next(); 
			v[i] = listeMots.get(mot);
			if(requete.contains(mot)){
				r[i++] = 1*ponderation[requete.indexOf(mot)];
			}else{
				r[i++] = 0;
			}
		}
		//on calcule le coeff de Salton
		double sum1 = 0, sum2 = 0, sum3 = 0;
		for(int j = 0; j< size; j++){
			sum1 += v[j]*r[j];
			sum2 += Math.pow(v[j], 2);
			sum3 += Math.pow(r[j], 2);
		}
		//cas où aucun mot de la requete n'est dans le document
		if(sum1 == 0){
			return 0;
		}
		return  sum1 /Math.sqrt((sum2*sum3));		
	}
	
	/**
	 * Fonction qui renvoie les coefficients de Salton de tous les documents de l'Index
	 * @param requete : la requete à envoyer
	 * @return une TreeMap avec pour clés les coefficients de Salton et pour objet le nom du document
	 */
	
	public TreeMap<Double, String> getSaltonCoeffs(Vector<String> requete){
		//double[] coeffs = new double[_index.size()];
		TreeMap<Double, String> coeffs = new TreeMap<Double,String>(); 
		for(Iterator<String> iteDoc = _index.keySet().iterator(); iteDoc.hasNext(); ){
			//coeffs[i++] = coeffSalton(requete, iteDoc.next());
			String document = iteDoc.next();
			double coeff;
			if((coeff = coeffSalton(requete,document)) > 0)
				coeffs.put(new Double(coeff),document );
		}
		
		return coeffs;
	}
	/**
	 * Fonction d'affichage d'un index
	 */

	@Override
	public String toString() {
		String retour = new String("-- Affichage de l'index --\n");
		for(Iterator<String> iteDoc = _index.keySet().iterator(); iteDoc.hasNext(); ){
			String doc = iteDoc.next();
			retour+="- " + doc + "\n";
			//Parcours de tous les mots pour ce document
			for(Iterator<String> iteMots = _index.get(doc).keySet().iterator(); iteMots.hasNext(); ){
				String mot = iteMots.next();
				retour+= "\t * "+ mot + " ==> " + _index.get(doc).get(mot) + "\n";
			}
		}
		return retour;
	}	
}
