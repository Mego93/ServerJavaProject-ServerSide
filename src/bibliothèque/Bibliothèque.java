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
	
	public String toStringAbonn�s() {
		String s = "";	
		for(int i = 1 ; i<abonn�s.size();i++) {
			s+=abonn�s.get(i).toString() + "\n";
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
