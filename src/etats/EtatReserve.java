/**
 * Classe d'état représentant un état de document réservé
 * @author VO Thierry & VYAS Ishan
 * @version 3.0
 */
package etats;

import java.util.Date;
import java.util.Timer;

import bibliothèque.Abonne;
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
	 * Réserve le document (dans ce cas là impossible)
	 * @param Un abonné
	 * @throws EmpruntException
	 */
	@Override
	public void reserver(Abonne ab) throws EmpruntException {
		throw new EmpruntException("Impossible de reserver un document reservé");
	}

	/**
	 * Emprunte le document
	 * Si un abonné ayant réservé ne correspond pas à l'abonné en paramètre
	 * cela jette une EmpruntException
	 * Sinon set l'état du Document en un nouvel objet EtatEmprunte
	 * et lance un TimerTask de bannissement avec un délai donné
	 * @param Un abonné
	 * @throws EmpruntException
	 */
	@Override
	public void emprunter(Abonne ab) throws EmpruntException {
		if (!aboReserve.equals(ab))
			throw new EmpruntException("L'abonné ne correspond pas à la reservation faite");
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
		return "Reservé";
	}
	

}
