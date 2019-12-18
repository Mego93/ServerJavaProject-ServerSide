package etats;

import bibliothèque.Abonne;
import documents.DocumentAbs;
import documents.Etat;
import exception.EmpruntException;
import exception.RetourException;


public class EtatEmprunte implements Etat {
	private DocumentAbs docPossession;
	
	public EtatEmprunte(Abonne aboPossession, DocumentAbs docPossession) {
		this.docPossession = docPossession;
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
		docPossession.setEtat(new EtatDisponible(docPossession));
	}
}