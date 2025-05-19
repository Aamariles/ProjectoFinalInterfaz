package co.edu.unbosque.util.exception;

public class EmptyListException extends Exception {
	private static final long serialVersionUID = 1L;

	public EmptyListException() {
		super("The list cannot be empty.");
	}

	public EmptyListException(String mesagge) {
		super(mesagge);
	}
}