package etats;

import java.util.Date;
import java.util.Timer;

import bibliothèque.Abonne;
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

	@Override
	public void reserver(Abonne ab) throws EmpruntException {
		docDisponible.setEtat(new EtatReserve(ab, docDisponible));

	}

	@Override
	public void emprunter(Abonne ab) throws EmpruntException {
		docDisponible.setEtat(new EtatEmprunte(ab, docDisponible, new Date()));
		Timer time = new Timer();
		time.schedule(new Bannissement(ab, time), DELAI_LIMITE);
	}

	@Override
	public void retour() throws RetourException {
		throw new RetourException("Impossible de retourner un document disponible");
	}

	@Override
	public String toString() {
		return "Disponible";
	}
	
	
}
