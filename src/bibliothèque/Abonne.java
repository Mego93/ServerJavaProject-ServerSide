package biblioth�que;

import exception.AbonnePuniException;

public class Abonne {
	private int numero;
	private String nom;
	private int age;
	private boolean estPuni;
	
	public Abonne(int numero, String nom,int age) {
		this.numero = numero;
		this.nom = nom;
		this.age = age;
		this.estPuni = false;
	}


	public int getNumero() {
		return numero;
	}

	public String getNom() {
		return nom;
	}


	public int getAge() {
		return age;
	}
	
	public boolean isPuni() {
		return estPuni;
	}
	
	public void punission() throws AbonnePuniException {
		if(estPuni)
			throw new AbonnePuniException("L'abonn� est d�j� puni");
		this.estPuni= true;
	}


	public void lib�rer() throws AbonnePuniException {
		if(!estPuni)
			throw new AbonnePuniException("L'abonn� n'est pas puni");
		this.estPuni= false;
		
	}

	


	
	
	

	
}
