package bibliothèque;

import exception.EmpruntException;
import exception.RetourException;

public interface Document {
	int numero();
	void reserver(Abonne ab) throws EmpruntException ;
	void emprunter(Abonne ab) throws EmpruntException;
	// retour document ou annulation réservation
	void retour() throws RetourException;
}
