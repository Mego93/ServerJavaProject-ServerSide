package documents;

import java.util.Timer;

import bibliothèque.Abonne;
import bibliothèque.Bibliothèque;
import bibliothèque.Document;
import exception.EmpruntException;
import exception.RetourException;

public abstract class DocumentAbs implements Document {
	private int numero;
	private String titre;
	private Bibliothèque bibliothèque;
	private boolean estDisponible;
	private boolean estReservé;
	private Abonne aboReservé;
	private Abonne aboPossession; // ?

	public DocumentAbs(int numero, String titre, Bibliothèque bibliothèque) {
		this.numero = numero;
		this.titre = titre;
		this.bibliothèque = bibliothèque;
		this.estReservé = false;
		this.estDisponible = true;
		this.aboReservé = null;
		this.aboPossession = null;
	}

	@Override
	public int numero() {
		return numero;
	}

	@Override
	public synchronized void reserver(Abonne ab) throws EmpruntException {
		if (!estDisponible)
			throw new EmpruntException("Le document est déjà emprunté");
		else if (estReservé)
			throw new EmpruntException("Le document est déjà reservé");
		else {
			this.estReservé = true;
			this.aboReservé = ab;

			Timer time = new Timer();
			time.schedule(new DocumentAttente(this, time), 20000);
		}

	}

	@Override
	public synchronized void emprunter(Abonne ab) throws EmpruntException {
		if (!estDisponible) {
			throw new EmpruntException("Le document n'est pas disponible");
		} else {
			if (this.estReservé) { // Si le document a déjà été reservé
				if (this.aboReservé.equals(ab)) {
					this.estDisponible = false;
					ab.getListeDocs().add(this);
				} else {
					throw new EmpruntException("Le document est reservé par un autre abonné");
				}
			} else { // En cas de non réservation
				this.estDisponible = false;
				this.aboPossession = ab;
			}

		}

	}

	@Override
	public synchronized void retour() throws RetourException {
		if (estDisponible || !estReservé)
			throw new RetourException("Le document n'est ni emprunté ni reservé");
		else if (this.estReservé) {
			this.estReservé = false;
			this.aboReservé = null;
		}
		this.estDisponible = true;
		this.aboPossession = null;
	}

}
