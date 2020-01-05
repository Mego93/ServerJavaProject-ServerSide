/**
 * Interface des �tats possible d'un document
 * @author VO Thierry & VYAS Ishan
 * @version 1.1
 */
package documents;

import biblioth�que.Abonne;
import exception.EmpruntException;
import exception.RetourException;

public interface Etat {

	void reserver(Abonne ab) throws EmpruntException;

	void emprunter(Abonne ab) throws EmpruntException;

	void retour() throws RetourException;
	
	String toString();
}
