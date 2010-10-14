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
 * @brief Classe qui permet teste les diff√©rents modules
 */

package index;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.Vector;

import org.xml.sax.SAXException;

/**
 * Classe principale
 * Lance un menu permettant: 
 * 		- d'indiquer la liste des fichier à indexer
 * 		- ouvrir un index
 * 		- effectuer une recherche
 * 		- Enregistrer l'index
 * @author Maxime Thoraval & Brizai Olivier
 *
 */
public class Main {

	static Index index = null;
	static ModuleRecherche moduleRecherche = null;

	public static void main(String[] args) {

		char choix = ' ';
		Scanner sc = new Scanner(System.in);

		while (choix != 'q'){
			try {
				
				if(index != null)
					System.out.println("\n[Index présent]");
				else
					System.out.println("\n[Aucun index, créez ou chargez en un]");
				System.out.println("Que souhaitez-vous faire ?");
				System.out.print("C)reer index\tO)uvrir index");
				if(index !=null)
					System.out.print("\tE)nregistrer l'index\tR)echercher");
				System.out.println("\tQ)uitter");
				choix = Character.toLowerCase(sc.next().charAt(0));

				switch (choix) {
				case 'c':
					System.out.print("Fichiers à indexer séparés par espace (10 textes présent par défaut) : ");
					sc.nextLine(); //Pour supprimer le "retour" de la sélection du menu
					creerIndex(sc.nextLine());
					System.out.println("Index crée ! \n");
					break;
					
				case 'o':
					System.out.print("Nom index (index.in par défaut) : ");
					sc.nextLine(); //Pour supprimer le "retour" de la sélection du menu
					chargerIndex(sc.nextLine());
					//index = chargerIndex();
					System.out.println("Index chargé ! \n");
					break;
					
				case 'e':
					System.out.print("Chemin du fichier (index.in par défaut) : ");
					sc.nextLine(); //Pour supprimer le "retour" de la sélection du menu
					saveIndex(sc.nextLine());
					System.out.println("Index sauvé ! \n");
					
					break;
				case 'r':
					if(moduleRecherche == null) {
						if(index != null)
							moduleRecherche = new ModuleRecherche(index, args);
						else {
							System.out.println("Créez ou ouvrez un index !\n");
							break;
						}
					}

					System.out.print("Mots à rechercher : ");
					sc.nextLine();
					rechercher(sc.nextLine());

					break;
				default:
					break;
				}
			} catch (IOException io) {
				System.out.println("** Fichier introuvable **");
			}
			catch (Exception e) {
				e.printStackTrace();
			} 

		}

	}

	/**
	 * Méthode pour initialiser l'index
	 * @param f La liste des fichiers (séparés par un espace) à indexé. Si pas de valeur, prend dans le corpus  La Fontaine
	 * @throws SAXException
	 * @throws IOException
	 */
	private static void creerIndex(String f) throws SAXException, IOException{
		String[] fichiers = {"../corpus/texte01.txt", "../corpus/texte02.txt","../corpus/texte03.txt","../corpus/texte04.txt",
				"../corpus/texte05.txt","../corpus/texte06.txt","../corpus/texte07.txt","../corpus/texte08.txt",
				"../corpus/texte09.txt","../corpus/texte10.txt"};
		if(!f.trim().equals(""))
			fichiers = f.split(" ");
		for(int i = 0; i <fichiers.length; i++){
			System.out.println(i + " " + fichiers[i]);
		}
		index = new Index(fichiers);
	}

	/**
	 * Méthode permettant d'effectuer une recherche
	 * @param recherches
	 */
	private static void rechercher(String recherches){
		String []m = recherches.split(" ");
		Vector<String> mots = new Vector<String>();
		for(int i = 0; i < m.length; i++)
			mots.add(m[i].toLowerCase());
		moduleRecherche.recherche(mots);
	}

	/**
	 * Méthode permettant de sauvegarder l'index
	 * @param url Chemin du fichier ou sauvegarder l'index
	 * @throws IOException
	 */
	private static void saveIndex(String url) throws IOException{
		if(url.trim().equals(""))
			url = "index.in";
		FileOutputStream fichier = new FileOutputStream(url);
		ObjectOutputStream oos = new ObjectOutputStream(fichier);
		oos.writeObject(index);
		oos.flush();
		oos.close();
	}

	/**
	 * Méthode permettant de charger un index (si pas de valeur, va chercher fichier 'index.in'
	 * @param url Chemin du fichier contenant l'index
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static void chargerIndex(String url) throws IOException, ClassNotFoundException{
		if(url.trim().equals(""))
			url = "index.in";
		FileInputStream fichier = new FileInputStream(url);
		ObjectInputStream ois = new ObjectInputStream(fichier);
		index = (Index)ois.readObject();
		ois.close();
	}
}
