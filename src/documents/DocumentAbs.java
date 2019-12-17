package documents;

import java.util.Timer;

import biblioth�que.Abonne;
import biblioth�que.Biblioth�que;
import biblioth�que.Document;
import exception.EmpruntException;
import exception.RetourException;

public abstract class DocumentAbs implements Document {
	private int numero;
	private String titre;
	private Biblioth�que biblioth�que;
	private boolean estDisponible;
	private boolean estReserv�;
	private Abonne aboReserv�;
	private Abonne aboPossession; // ?

	public DocumentAbs(int numero, String titre, Biblioth�que biblioth�que) {
		this.numero = numero;
		this.titre = titre;
		this.biblioth�que = biblioth�que;
		this.estReserv� = false;
		this.estDisponible = true;
		this.aboReserv� = null;
		this.aboPossession = null;
	}

	@Override
	public int numero() {
		return numero;
	}

	@Override
	public synchronized void reserver(Abonne ab) throws EmpruntException {
		if (!estDisponible)
			throw new EmpruntException("Le document est d�j� emprunt�");
		else if (estReserv�)
			throw new EmpruntException("Le document est d�j� reserv�");
		else {
			this.estReserv� = true;
			this.aboReserv� = ab;

			Timer time = new Timer();
			time.schedule(new DocumentAttente(this, time), 20000);
		}

	}

	@Override
	public synchronized void emprunter(Abonne ab) throws EmpruntException {
		if (!estDisponible) {
			throw new EmpruntException("Le document n'est pas disponible");
		} else {
			if (this.estReserv�) { // Si le document a d�j� �t� reserv�
				if (this.aboReserv�.equals(ab)) {
					this.estDisponible = false;
					ab.getListeDocs().add(this);
				} else {
					throw new EmpruntException("Le document est reserv� par un autre abonn�");
				}
			} else { // En cas de non r�servation
				this.estDisponible = false;
				this.aboPossession = ab;
			}

		}

	}

	@Override
	public synchronized void retour() throws RetourException {
		if (estDisponible || !estReserv�)
			throw new RetourException("Le document n'est ni emprunt� ni reserv�");
		else if (this.estReserv�) {
			this.estReserv� = false;
			this.aboReserv� = null;
		}
		this.estDisponible = true;
		this.aboPossession = null;
	}

}
