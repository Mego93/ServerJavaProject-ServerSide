/**
 * Classe d'exception d'emprunt
 * @author VO Thierry & VYAS Ishan
 * @version 1.0
 */
package exception;

public class EmpruntException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String string;


	public EmpruntException(String string) {
		this.string = string;
	}
	
	@Override
	public String toString() {
		return string;
	}


}
