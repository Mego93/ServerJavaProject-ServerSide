/**
 * Classe du livre
 * H�rite de DocumentAbs
 * @author VO Thierry & VYAS Ishan
 * @version 1.5
 */
package documents;

import biblioth�que.Biblioth�que;

public class Livre extends DocumentAbs {

	public Livre(int numero, String titre, Biblioth�que biblioth�que) {
		super(numero, titre, biblioth�que);
	}
	
	@Override
	public String toString() {
		String s = "";
		s+="Livre " + super.toString();
		return s;
	}

}
