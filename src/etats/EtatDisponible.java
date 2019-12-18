package etats;

import bibliothèque.Abonne;
import documents.DocumentAbs;
import documents.Etat;
import exception.EmpruntException;
import exception.RetourException;

public class EtatDisponible implements Etat {
	private DocumentAbs docDisponible;

	public EtatDisponible(DocumentAbs docDisponible) {
		this.docDisponible = docDisponible;
	}

	@Override
	public void reserver(Abonne ab) throws EmpruntException {
		docDisponible.setEtat(new EtatReserve(ab, docDisponible));

	}

	@Override
	public void emprunter(Abonne ab) throws EmpruntException {
		docDisponible.setEtat(new EtatEmprunte(ab, docDisponible));

	}

	@Override
	public void retour() throws RetourException {
		throw new RetourException("Impossible de retourner un document disponible");
	}
}
