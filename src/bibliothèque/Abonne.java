package bibliothèque;

import java.util.ArrayList;

public class Abonne {
	private int numero;
	private String nom;
	private ArrayList<Document> listeDocs; // Inutile ?
	
	public Abonne(int numero, String nom) {
		this.numero = numero;
		this.nom = nom;
		this.listeDocs = new ArrayList<>();
	}


	public int getNumero() {
		return numero;
	}

	public String getNom() {
		return nom;
	}

	public ArrayList<Document> getListeDocs() {
		return listeDocs;
	}


	
	
	

	
}
