package co.edu.unbosque.util.exception;

public class ExistingUserException extends Exception {
	private static final long serialVersionUID = 1L;

	public ExistingUserException(String mesagge) {
		super(mesagge);
	}
}