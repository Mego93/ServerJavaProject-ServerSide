/**
 * Classe d'�tat repr�sentant un �tat de document emprunt�
 * @author VO Thierry & VYAS Ishan
 * @version 3.1
 */
package etats;

import java.util.Date;
import java.util.Timer;

import biblioth�que.Abonne;
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
	 * R�serve le document (dans ce cas l� impossible)
	 * @param Un abonn�
	 * @throws EmpruntException
	 */
	@Override
	public void reserver(Abonne ab) throws EmpruntException {
		throw new EmpruntException("Impossible de reserver un document emprunt�");

	}

	/**
	 * Emprunte le document (dans ce cas l� impossible)
	 * @param Un abonn�
	 * @throws EmpruntException
	 */
	@Override
	public void emprunter(Abonne ab) throws EmpruntException {
		throw new EmpruntException("Impossible d'emprunter un document emprunt�");

	}

	/**
	 * Retourne le document
	 * Si la diff�rence entre la date d'emprunt et la date de retour est sup�rieur
	 * au d�lai donn�, l'abonn� ayant retourn� le document est banni
	 * Sinon jette une AbonnePuniException (catch)
	 * Enfin, la m�thode lance un TimerTask de "d�bannissement" avec un d�lai donn�
	 * et set l'�tat du Document en un nouvel objet EtatDisponible
	 * @param Un abonn�
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
		return "Emprunt�";
	}

}