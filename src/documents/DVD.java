/**
 * Classe du DVD
 * Hérite de DocumentAbs
 * @author VO Thierry & VYAS Ishan
 * @version 1.5
 */
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
	
	/**
	 * Réserve le document
	 * Si l'age de l'abonné est inférieur à l'age requis pour ce DVD
	 * Jette une EmpruntException
	 * @throws EmpruntException
	 */
	@Override
	public synchronized void reserver(Abonne ab) throws EmpruntException {
		if(ab.getAge() < this.ageMini)
			throw new EmpruntException("L'âge minimum pour réserver ce DVD n'est pas atteint");
		super.reserver(ab);
	}

	/**
	 * Emprunte le document
	 * Si l'age de l'abonné est inférieur à l'age requis pour ce DVD
	 * Jette une EmpruntException
	 * @throws EmpruntException
	 */
	@Override
	public synchronized void emprunter(Abonne ab) throws EmpruntException {
		if(ab.getAge() < this.ageMini)
			throw new EmpruntException("L'âge minimum pour emprunter ce DVD n'est pas atteint");
		super.emprunter(ab);
	}

	@Override
	public String toString() {
		String s = "";
		s += "Document n° " + numero() + ", titre : '" + getTitre() + "', état du document = " + getEtat().toString() + "\n";
		return s;
	}

}
