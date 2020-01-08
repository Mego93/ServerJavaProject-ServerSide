/**
 * Classe du livre
 * Hérite de DocumentAbs
 * @author VO Thierry & VYAS Ishan
 * @version 1.5
 */
package documents;

import bibliothèque.Bibliothèque;

public class Livre extends DocumentAbs {

	public Livre(int numero, String titre, Bibliothèque bibliothèque) {
		super(numero, titre, bibliothèque);
	}
	
	@Override
	public String toString() {
		String s = "";
		s+="Livre " + super.toString();
		return s;
	}

}
