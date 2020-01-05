/**
 * Classe d'�tat repr�sentant un �tat de document disponible
 * @author VO Thierry & VYAS Ishan
 * @version 3.3
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

public class EtatDisponible implements Etat {
	private static final long DELAI_LIMITE = 1314001440L;  // 1314001440L = 2 semaines  
	private DocumentAbs docDisponible;

	public EtatDisponible(DocumentAbs docDisponible) {
		this.docDisponible = docDisponible;
	}

	/**
	 * R�serve le document
	 * Set l'�tat du Document en un nouvel objet EtatReserve
	 * @param Un abonn�
	 * @throws EmpruntException
	 */
	@Override
	public void reserver(Abonne ab) throws EmpruntException {
		docDisponible.setEtat(new EtatReserve(ab, docDisponible));

	}

	/**
	 * Emprunte le document
	 * Set l'�tat du Document en un nouvel objet EtatEmprunte
	 * et lance un TimerTask de bannissement avec un d�lai donn�
	 * @param Un abonn�
	 * @throws EmpruntException
	 */
	@Override
	public void emprunter(Abonne ab) throws EmpruntException {
		docDisponible.setEtat(new EtatEmprunte(ab, docDisponible, new Date()));
		Timer time = new Timer();
		time.schedule(new Bannissement(ab, time), DELAI_LIMITE);
	}

	/**
	 * Retourne le document (dans ce cas l� impossible)
	 * @throws RetourException
	 */
	@Override
	public void retour() throws RetourException {
		throw new RetourException("Impossible de retourner un document disponible");
	}

	@Override
	public String toString() {
		return "Disponible";
	}
	
	
}
