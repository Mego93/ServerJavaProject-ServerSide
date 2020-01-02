package exception;

public class AbonnePuniException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String string;


	public AbonnePuniException(String string) {
		this.string = string;
	}
	
	@Override
	public String toString() {
		return string;
	}


}
