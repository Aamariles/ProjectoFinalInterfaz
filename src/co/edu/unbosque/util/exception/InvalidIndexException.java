package co.edu.unbosque.util.exception;

public class InvalidIndexException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidIndexException() {
		super("Índex not valid.");
	}

	public InvalidIndexException(String mesagge) {
		super(mesagge);
	}
}