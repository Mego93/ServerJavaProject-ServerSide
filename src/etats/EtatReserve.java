package etats;

import java.util.Date;
import java.util.Timer;

import biblioth�que.Abonne;
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

	@Override
	public void reserver(Abonne ab) throws EmpruntException {
		throw new EmpruntException("Impossible de reserver un document reserv�");
	}

	@Override
	public void emprunter(Abonne ab) throws EmpruntException {
		if (!aboReserve.equals(ab))
			throw new EmpruntException("L'abonn� ne correspond pas � la reservation faite");
		else {
			docReserve.setEtat(new EtatEmprunte(ab, docReserve, new Date()));
			Timer time = new Timer();
			time.schedule(new Bannissement(aboReserve, time), DELAI_LIMITE);
		}

	}

	@Override
	public void retour() throws RetourException {
		docReserve.setEtat(new EtatDisponible(docReserve));
	}
	

	@Override
	public String toString() {
		return "Reserv�";
	}
	

}
