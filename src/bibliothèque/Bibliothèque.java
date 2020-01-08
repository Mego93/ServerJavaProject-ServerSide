/**
 * Classe de la biblioth�que
 * @author VO Thierry & VYAS Ishan
 * @version 2.0
 */
package biblioth�que;

import java.util.HashMap;
import java.util.Map.Entry;

public class Biblioth�que {
	private static HashMap<Integer,Document> biblio;
	private static HashMap<Integer, Abonne> abonn�s;
	private static HashMap<Document, Abonne> listeAttente;

	public Biblioth�que() {
		Biblioth�que.biblio = new HashMap<Integer, Document>();
		Biblioth�que.abonn�s = new HashMap<Integer, Abonne>();
		Biblioth�que.listeAttente = new HashMap<Document,Abonne>();
	}

	public HashMap<Integer, Document> getBiblio() {
		return biblio;
	}

	public HashMap<Integer, Abonne> getAbonn�s() {
		return abonn�s;
	}
	
	public static HashMap<Document, Abonne> getListeAttente() {
		return listeAttente;
	}

	public String toStringAbonn�s() {
		StringBuilder sb = new StringBuilder();	
		sb.append("La biblioth�que VYOS a les abonn�s suivants : \n");
		for(Entry<Integer, Abonne> abonne : Biblioth�que.abonn�s.entrySet()) {
			sb.append(abonne.getValue().toString() + "\n");
		}
		return sb.toString();
	}
	
	public String toStringDocs() {
		StringBuilder sb = new StringBuilder();	
		sb.append("La biblioth�que VYOS poss�de les documents suivants : \n");
		for(Entry<Integer, Document> document : Biblioth�que.biblio.entrySet()) {
			sb.append(document.getValue().toString() + "\n");
		}
		return sb.toString();
	}
	
	
}
