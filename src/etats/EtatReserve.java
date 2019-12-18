package etats;

import bibliothèque.Abonne;
import documents.DocumentAbs;
import documents.Etat;
import exception.EmpruntException;
import exception.RetourException;

public class EtatReserve implements Etat {
	private Abonne aboReserve;
	private DocumentAbs docReserve;

	public EtatReserve(Abonne aboReserve, DocumentAbs docReserve) {
		this.aboReserve = aboReserve;
		this.docReserve = docReserve;
	}

	@Override
	public void reserver(Abonne ab) throws EmpruntException {
		throw new EmpruntException("");
	}

	@Override
	public void emprunter(Abonne ab) throws EmpruntException {
		if (!aboReserve.equals(ab))
			throw new EmpruntException("");
		else
			docReserve.setEtat(new EtatEmprunte(ab, docReserve));

	}

	@Override
	public void retour() throws RetourException {
		docReserve.setEtat(new EtatDisponible(docReserve));

	}

}
