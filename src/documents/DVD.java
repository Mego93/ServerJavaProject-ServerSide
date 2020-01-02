package documents;

import biblioth�que.Abonne;
import biblioth�que.Biblioth�que;
import exception.EmpruntException;

public class DVD extends DocumentAbs {
	private int ageMini;
	
	public DVD(int numero, String titre, Biblioth�que biblioth�que,int ageMini) {
		super(numero, titre, biblioth�que);
		this.ageMini = ageMini;
	}
	
	@Override
	public synchronized void reserver(Abonne ab) throws EmpruntException {
		if(ab.getAge() < this.ageMini)
			throw new EmpruntException("L'�ge minimum pour r�server ce DVD n'est pas atteint");
		super.reserver(ab);
	}

	@Override
	public synchronized void emprunter(Abonne ab) throws EmpruntException {
		if(ab.getAge() < this.ageMini)
			throw new EmpruntException("L'�ge minimum pour emprunter ce DVD n'est pas atteint");
		super.emprunter(ab);
	}

	@Override
	public String toString() {
		String s = "";
		s += "Document (DVD) n� " + super.numero() + ", titre : '" + super.getTitre() + "', �ge minimum : "+this.ageMini + ", �tat du document ; ";
		switch (this.getEtat().toString()) {
		case ("Reserv�"):
			s += "Reserv�\n";
		case ("Emprunt�"):
			s += "Emprunt�\n";
		case ("Disponible"):
			s += "Disponible\n";
		}
		return s;
	}

}
