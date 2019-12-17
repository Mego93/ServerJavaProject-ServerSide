package exception;

public class RetourException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String string;


	public RetourException(String string) {
		this.string = string;
	}
	
	@Override
	public String toString() {
		return string;
	}

}
