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
 * @file Eleve.java
 * @brief Classe représentant un élève
 */

package ensicaen.tb.mvc.eleves.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Eleve implements Serializable {

	private int id;
	private int version;
	private String nom;
	private String prenom;
	private Date dateNaissance;
	private boolean redoublant;
	private int annee;
	private String filiere;
	
	/**
	* Constructeur par défaut d'un élève
	*/

	public Eleve(){
	}
	
	/**
	* Constructeur d'un élève
	* @param Le nom de l'élève
	* @param Le prénom
	* @param La date de naissance
	* @param S'il est redoublant (true or false)
	* @param En quelle année il est (1, 2 ou 3)
	* @param Sa filière (INFO, ELEC, MCF)
	*/

	public Eleve(String nom, String prenom,
			Date dateNaissance, boolean redoublant, int annee, String filiere) {
		this(-1, 1, nom, prenom, dateNaissance, redoublant, annee, filiere);
	}
	
	/**
	* Constructeur d'un élève
	* @param Son identifiant 
	* @param Sa version
	* @param Le nom de l'élève
	* @param Le prénom
	* @param La date de naissance
	* @param S'il est redoublant (true or false)
	* @param En quelle année il est (1, 2 ou 3)
	* @param Sa filière (INFO, ELEC, MCF)
	*/

	public Eleve(int id, int version, String nom, String prenom,
			Date dateNaissance, boolean redoublant, int annee, String filiere) {
		this.id = id;
		this.version = version;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.redoublant = redoublant;
		this.annee = annee;
		this.filiere = filiere;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getVersion() {
		return version;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public Date getDateNaissance() {
		return dateNaissance;
	}
	
	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	
	public boolean isRedoublant() {
		return redoublant;
	}
	
	public void setRedoublant(boolean redoublant) {
		this.redoublant = redoublant;
	}
	
	public int getAnnee() {
		return annee;
	}
	
	public void setAnnee(int annee) {
		this.annee = annee;
	}
	
	public String getFiliere() {
		return filiere;
	}
	
	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}
	
	@Override
	public String toString() {
		String retour = "Eleve " + nom + " " + prenom;
		retour += "\nId : " + id;
		retour += "\nVersion : " + version;
		retour += "\nDate de naissance : " + new SimpleDateFormat("dd/MM/yyyy").format(dateNaissance);
		retour += "\nRedoublant : " + Boolean.toString(redoublant);
		retour += "\nAnnée : " + annee;
		retour += "\nFilière : " + filiere;
		
		return retour;
	}
	
	public boolean equals(Object obj) {
		Eleve comp = (Eleve)obj;
		if(comp.getId() == this.id && comp.getVersion() == this.version && comp.getNom().equals(this.nom)
				&& comp.getPrenom().equals(this.prenom) && comp.isRedoublant()== this.redoublant
				&& comp.getDateNaissance().equals(this.dateNaissance) && comp.getFiliere().equals(this.filiere))
			return true;
		return false;
	}
	
}
