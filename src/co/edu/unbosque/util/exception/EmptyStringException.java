package co.edu.unbosque.util.exception;

public class EmptyStringException extends Exception {
	private static final long serialVersionUID = 1L;

	public EmptyStringException() {
		super("Text cannot be empty.");
	}

	public EmptyStringException(String mesagge) {
		super(mesagge);
	}

	public EmptyStringException(String mesagge, Throwable cause) {
		super(mesagge, cause);
	}
}