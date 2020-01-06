/**
 * Classe d'�tat repr�sentant un �tat de document r�serv�
 * @author VO Thierry & VYAS Ishan
 * @version 3.0
 */
package etats;

import java.util.Date;
import java.util.Timer;

import biblioth�que.Abonne;
import documents.DocumentAbs;
import documents.Etat;
import exception.EmpruntException;
import exception.RetourException;
import timertasks.Bannissement;

public class EtatReserve implements Etat {
	private static final long DELAI_LIMITE = 1314001440L; // 2 semaines
	private Abonne aboReserve;
	private DocumentAbs docReserve;

	public EtatReserve(Abonne aboReserve, DocumentAbs docReserve) {
		this.aboReserve = aboReserve;
		this.docReserve = docReserve;
	}
	
	/**
	 * R�serve le document (dans ce cas l� impossible)
	 * @param Un abonn�
	 * @throws EmpruntException
	 */
	@Override
	public void reserver(Abonne ab) throws EmpruntException {
		throw new EmpruntException("Impossible de reserver un document reserv�");
	}

	/**
	 * Emprunte le document
	 * Si un abonn� ayant r�serv� ne correspond pas � l'abonn� en param�tre
	 * cela jette une EmpruntException
	 * Sinon set l'�tat du Document en un nouvel objet EtatEmprunte
	 * et lance un TimerTask de bannissement avec un d�lai donn�
	 * @param Un abonn�
	 * @throws EmpruntException
	 */
	@Override
	public void emprunter(Abonne ab) throws EmpruntException {
		if (!aboReserve.equals(ab))
			throw new EmpruntException("L'abonn� ne correspond pas � la reservation faite");
		else {
			docReserve.setEtat(new EtatEmprunte(ab, docReserve, new Date()));
			Timer time = new Timer();
			time.schedule(new Bannissement(aboReserve, time), DELAI_LIMITE);
		}

	}
	
	/**
	 * Retourne le document
	 * @throws RetourException
	 */
	@Override
	public void retour() throws RetourException {
		docReserve.setEtat(new EtatDisponible(docReserve));
	}
	

	@Override
	public String toString() {
		return "Reserv�";
	}
	

}
