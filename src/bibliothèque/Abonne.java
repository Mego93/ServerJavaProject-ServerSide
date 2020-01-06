/**
 * Classe de l'abonn�
 * @author VO Thierry & VYAS Ishan
 * @version 2.0
 */
package biblioth�que;

import exception.AbonnePuniException;

public class Abonne {
	private int numero;
	private String email;
	private String nom;
	private int age;
	private boolean estPuni;
	
	public Abonne(int numero, String nom,int age,String email) {
		this.numero = numero;
		this.nom = nom;
		this.age = age;
		this.estPuni = false;
		this.email = email;
	}


	public String getEmail() {
		return email;
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
	 * M�thode de punission de l'abonn�
	 * S'il est d�j� puni
	 * Jette une AbonnePuniException
	 * @throws AbonnePuniException
	 */
	public void punission() throws AbonnePuniException {
		if(estPuni)
			throw new AbonnePuniException("L'abonn� est d�j� puni");
		this.estPuni= true;
	}

	/**
	 * M�thode de "lib�ration" de l'abonn�
	 * S'il n'est pas puni
	 * Jette une AbonnePuniException
	 * @throws AbonnePuniException
	 */
	public void lib�rer() throws AbonnePuniException {
		if(!estPuni)
			throw new AbonnePuniException("L'abonn� n'est pas puni");
		this.estPuni= false;
		
	}
	
}
