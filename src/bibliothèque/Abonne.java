/**
 * Classe de l'abonné
 * @author VO Thierry & VYAS Ishan
 * @version 2.0
 */
package bibliothèque;

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
		
	/**
	 * Méthode de punission de l'abonné
	 * S'il est déjà puni
	 * Jette une AbonnePuniException
	 * @throws AbonnePuniException
	 */
	public void punission() throws AbonnePuniException {
		if(estPuni)
			throw new AbonnePuniException("L'abonné est déjà puni");
		this.estPuni= true;
	}

	/**
	 * Méthode de "libération" de l'abonné
	 * S'il n'est pas puni
	 * Jette une AbonnePuniException
	 * @throws AbonnePuniException
	 */
	public void libérer() throws AbonnePuniException {
		if(!estPuni)
			throw new AbonnePuniException("L'abonné n'est pas puni");
		this.estPuni= false;
		
	}

	


	
	
	

	
}
