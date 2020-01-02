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
	
	public String toStringAbonnés() {
		String s = "";	
		for(int i = 1 ; i<abonnés.size();i++) {
			s+=abonnés.get(i).toString() + "\n";
		}
		return s;
	}
	
	public String toStringDocs() {
		StringBuilder sb = new StringBuilder();	
		for(int i = 1 ; i<biblio.size();i++) {
			sb.append(biblio.get(i).toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	
}
