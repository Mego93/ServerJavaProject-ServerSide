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

	@Override
	public void reserver(Abonne ab) throws EmpruntException {
		throw new EmpruntException("Impossible de reserver un document emprunté");

	}

	@Override
	public void emprunter(Abonne ab) throws EmpruntException {
		throw new EmpruntException("Impossible d'emprunter un document emprunté");

	}

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