package co.edu.unbosque.util.exception;

public class InvalidOptionException extends Exception{
	private static final long serialVersionUID = 1L;

	public InvalidOptionException() {
		super("Invalid option, try again.");
	}

	public InvalidOptionException(String mesagge) {
		super(mesagge);
	}
}

