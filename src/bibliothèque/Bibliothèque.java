package bibliothèque;

import java.util.HashMap;

public class Bibliothèque {
	private static HashMap<Integer,Document> biblio;
	private static HashMap<Integer, Abonne> abonnés;
	

	public Bibliothèque() {
		Bibliothèque.biblio = new HashMap<Integer, Document>();
		Bibliothèque.abonnés = new HashMap<Integer, Abonne>();
	}

	public HashMap<Integer, Document> getBiblio() {
		return biblio;
	}

	public HashMap<Integer, Abonne> getAbonnés() {
		return abonnés;
	}
	
	
	
}
