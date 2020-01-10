/**
 * Classe d'�tat repr�sentant un �tat de document emprunt�
 * @author VO Thierry & VYAS Ishan
 * @version 3.1
 */
package etats;

import java.util.Date;
import java.util.Map.Entry;
import java.util.Timer;

import biblioth�que.Abonne;
import biblioth�que.Biblioth�que;
import biblioth�que.Document;
import biblioth�que.Mail;
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
	 * L'abonn� est sanctionn� s'il d�passe le d�lai donn� pour le retour,
	 * ou s'il rend un document abim� (ici un random)
	 * Sinon jette une AbonnePuniException (catch)
	 * Enfin, la m�thode lance un TimerTask de "d�bannissement" avec un d�lai donn�
	 * set l'�tat du Document en un nouvel objet EtatDisponible et envoie un mail
	 * de rappel � la liste d'attente du document
	 * @param Un abonn�
	 * @throws RetourException
	 */
	@Override
	public void retour() throws RetourException {
		Date dateRetour = new Date();
		double probaSanction = Math.random();
		if (dateRetour.getTime() - this.dateEmprunt.getTime() > DELAI_LIMITE || probaSanction < 0.05) {
			try {
				this.aboPossession.punission();
			} catch (AbonnePuniException e) {
				e.printStackTrace();
			} finally {
				Timer time = new Timer();
				time.schedule(new Debannissement(this.aboPossession, time), DELAI_DEBANNISSEMENT);
			}
		}
		docPossession.getBiblioth�que();
		for(Entry<Document, Abonne> a : Biblioth�que.getListeAttente().entrySet())
			Mail.sendMail(a.getValue(), docPossession);
		docPossession.setEtat(new EtatDisponible(docPossession));
	}

	@Override
	public String toString() {
		return "Emprunt�";
	}

}