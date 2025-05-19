package co.edu.unbosque.util.exception;

public class NegativeNumberException extends Exception {
	private static final long serialVersionUID = 1L;

	public NegativeNumberException() {
		super("The numbers cannot be negatives.");
	}

	public NegativeNumberException(String mesagge) {
		super(mesagge);
	}
}