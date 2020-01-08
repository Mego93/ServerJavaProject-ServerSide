/**
 * Classe de l'abonn�
 * @author VO Thierry & VYAS Ishan
 * @version 2.1
 */
package biblioth�que;

import java.time.LocalDate;
import java.time.Period;

import exception.AbonnePuniException;

public class Abonne {
	private int numero;
	private String email;
	private String nom;
	private LocalDate dateNaissance;
	private boolean estPuni;
	
	public Abonne(int numero, String nom,String dateNaissance,String email) {
		this.numero = numero;
		this.nom = nom;
		this.dateNaissance = LocalDate.parse(dateNaissance);
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
		LocalDate today = LocalDate.now();       
		return Period.between(this.dateNaissance,today).getYears();
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
