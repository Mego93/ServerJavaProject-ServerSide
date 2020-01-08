/**
 * Classe de la bibliothèque
 * @author VO Thierry & VYAS Ishan
 * @version 2.0
 */
package bibliothèque;

import java.util.HashMap;
import java.util.Map.Entry;

public class Bibliothèque {
	private static HashMap<Integer,Document> biblio;
	private static HashMap<Integer, Abonne> abonnés;
	private static HashMap<Document, Abonne> listeAttente;

	public Bibliothèque() {
		Bibliothèque.biblio = new HashMap<Integer, Document>();
		Bibliothèque.abonnés = new HashMap<Integer, Abonne>();
		Bibliothèque.listeAttente = new HashMap<Document,Abonne>();
	}

	public HashMap<Integer, Document> getBiblio() {
		return biblio;
	}

	public HashMap<Integer, Abonne> getAbonnés() {
		return abonnés;
	}
	
	public static HashMap<Document, Abonne> getListeAttente() {
		return listeAttente;
	}

	public String toStringAbonnés() {
		StringBuilder sb = new StringBuilder();	
		for(int i = 1 ; i<abonnés.size();i++) {
			sb.append(abonnés.get(i).toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public String toStringDocs() {
		StringBuilder sb = new StringBuilder();	
		for(Entry<Integer, Document> document : Bibliothèque.biblio.entrySet()) {
			sb.append(document.getValue().toString());
		}
		return sb.toString();
	}
	
	
}
