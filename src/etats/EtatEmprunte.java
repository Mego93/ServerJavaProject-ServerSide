/**
 * Classe d'état représentant un état de document emprunté
 * @author VO Thierry & VYAS Ishan
 * @version 3.1
 */
package etats;

import java.util.Date;
import java.util.Timer;

import bibliothèque.Abonne;
import documents.DocumentAbs;
import documents.Etat;
import exception.AbonnePuniException;
import exception.EmpruntException;
import exception.RetourException;
import timertasks.Debannissement;

public class EtatEmprunte implements Etat {
	private static final long DELAI_DEBANNISSEMENT = 657000720L; // 657000720L = 1 semaine
	private static final long DELAI_LIMITE = 1314001440L; // 1314001440L = 2 semaines  
	private Abonne aboPossession;
	private DocumentAbs docPossession;
	private Date dateEmprunt;

	public EtatEmprunte(Abonne aboPossession, DocumentAbs docPossession, Date dateEmprunt) {
		this.aboPossession = aboPossession;
		this.docPossession = docPossession;
		this.dateEmprunt = dateEmprunt;
	}

	/**
	 * Réserve le document (dans ce cas là impossible)
	 * @param Un abonné
	 * @throws EmpruntException
	 */
	@Override
	public void reserver(Abonne ab) throws EmpruntException {
		throw new EmpruntException("Impossible de reserver un document emprunté");

	}

	/**
	 * Emprunte le document (dans ce cas là impossible)
	 * @param Un abonné
	 * @throws EmpruntException
	 */
	@Override
	public void emprunter(Abonne ab) throws EmpruntException {
		throw new EmpruntException("Impossible d'emprunter un document emprunté");

	}

	/**
	 * Retourne le document
	 * Si la différence entre la date d'emprunt et la date de retour est supérieur
	 * au délai donné, l'abonné ayant retourné le document est banni
	 * Sinon jette une AbonnePuniException (catch)
	 * Enfin, la méthode lance un TimerTask de "débannissement" avec un délai donné
	 * et set l'état du Document en un nouvel objet EtatDisponible
	 * @param Un abonné
	 * @throws RetourException
	 */
	@Override
	public void retour() throws RetourException {
		Date dateRetour = new Date();
		if (dateRetour.getTime() - this.dateEmprunt.getTime() > DELAI_LIMITE) {
			try {
				this.aboPossession.punission();
			} catch (AbonnePuniException e) {
				e.printStackTrace();
			} finally {
				Timer time = new Timer();
				time.schedule(new Debannissement(this.aboPossession, time), DELAI_DEBANNISSEMENT);
			}
		}
		docPossession.setEtat(new EtatDisponible(docPossession));
	}

	@Override
	public String toString() {
		return "Emprunté";
	}

}