package documents;

import bibliothèque.Abonne;
import bibliothèque.Bibliothèque;
import bibliothèque.Document;
import etats.EtatDisponible;
import exception.EmpruntException;
import exception.RetourException;

public abstract class DocumentAbs implements Document {
	private int numero;
	private String titre;
	private Bibliothèque bibliothèque;
	private Etat etatDoc;

	public DocumentAbs(int numero, String titre, Bibliothèque bibliothèque) {
		this.numero = numero;
		this.titre = titre;
		this.bibliothèque = bibliothèque;
		this.etatDoc = new EtatDisponible(this);
	}

	@Override
	public int numero() {
		return numero;
	}

	@Override
	public synchronized void reserver(Abonne ab) throws EmpruntException {
		etatDoc.reserver(ab);
	}

	@Override
	public synchronized void emprunter(Abonne ab) throws EmpruntException {
		etatDoc.emprunter(ab);
	}

	@Override
	public synchronized void retour() throws RetourException {
		etatDoc.retour();
	}

	public void setEtat(Etat etat) {
		this.etatDoc = etat;
	}

	public Etat getEtat() {
		return etatDoc;
	}

	public String getTitre() {
		return titre;
	}

	@Override
	public String toString() {
		String s = "";
		s += "Document n° " + numero + ", titre : '" + titre + "', état du document = " + etatDoc.toString() + "\n";
		return s;
	}

	public Bibliothèque getBibliothèque() {
		return bibliothèque;
	}

}
