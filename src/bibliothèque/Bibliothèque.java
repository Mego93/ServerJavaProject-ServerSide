package biblioth�que;

import java.util.HashMap;

public class Biblioth�que {
	private static HashMap<Integer,Document> biblio;
	private static HashMap<Integer, Abonne> abonn�s;
	

	public Biblioth�que() {
		Biblioth�que.biblio = new HashMap<Integer, Document>();
		Biblioth�que.abonn�s = new HashMap<Integer, Abonne>();
	}

	public HashMap<Integer, Document> getBiblio() {
		return biblio;
	}

	public HashMap<Integer, Abonne> getAbonn�s() {
		return abonn�s;
	}
	
	
	
}
