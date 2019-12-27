package documents;

import bibliothèque.Abonne;
import bibliothèque.Bibliothèque;
import exception.EmpruntException;

public class DVD extends DocumentAbs {
	private int ageMini;
	
	public DVD(int numero, String titre, Bibliothèque bibliothèque,int ageMini) {
		super(numero, titre, bibliothèque);
		this.ageMini = ageMini;
	}
	
	@Override
	public synchronized void reserver(Abonne ab) throws EmpruntException {
		if(ab.getAge() < this.ageMini)
			throw new EmpruntException("L'âge minimum pour réserver ce DVD n'est pas atteint");
		super.reserver(ab);
	}

	@Override
	public synchronized void emprunter(Abonne ab) throws EmpruntException {
		if(ab.getAge() < this.ageMini)
			throw new EmpruntException("L'âge minimum pour emprunter ce DVD n'est pas atteint");
		super.emprunter(ab);
	}

}
