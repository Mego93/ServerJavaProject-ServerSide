/**
 * Classe du DVD
 * H�rite de DocumentAbs
 * @author VO Thierry & VYAS Ishan
 * @version 1.5
 */
package documents;

import biblioth�que.Abonne;
import biblioth�que.Biblioth�que;
import exception.EmpruntException;

public class DVD extends DocumentAbs {
	private int ageMini;
	
	public DVD(int numero, String titre, Biblioth�que biblioth�que,int ageMini) {
		super(numero, titre, biblioth�que);
		this.ageMini = ageMini;
	}
	
	/**
	 * R�serve le document
	 * Si l'age de l'abonn� est inf�rieur � l'age requis pour ce DVD
	 * Jette une EmpruntException
	 * @throws EmpruntException
	 */
	@Override
	public synchronized void reserver(Abonne ab) throws EmpruntException {
		if(ab.getAge() < this.ageMini)
			throw new EmpruntException("L'�ge minimum pour r�server ce DVD n'est pas atteint");
		super.reserver(ab);
	}

	/**
	 * Emprunte le document
	 * Si l'age de l'abonn� est inf�rieur � l'age requis pour ce DVD
	 * Jette une EmpruntException
	 * @throws EmpruntException
	 */
	@Override
	public synchronized void emprunter(Abonne ab) throws EmpruntException {
		if(ab.getAge() < this.ageMini)
			throw new EmpruntException("L'�ge minimum pour emprunter ce DVD n'est pas atteint");
		super.emprunter(ab);
	}

	@Override
	public String toString() {
		String s = "";
		s += "Document n� " + numero() + ", titre : '" + getTitre() + "', �tat du document = " + getEtat().toString() + "\n";
		return s;
	}

}
