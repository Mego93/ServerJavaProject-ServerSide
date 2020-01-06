package documents;

import biblioth�que.Abonne;
import exception.EmpruntException;
import exception.RetourException;

public interface Etat {

	void reserver(Abonne ab) throws EmpruntException;

	void emprunter(Abonne ab) throws EmpruntException;

	void retour() throws RetourException;
}