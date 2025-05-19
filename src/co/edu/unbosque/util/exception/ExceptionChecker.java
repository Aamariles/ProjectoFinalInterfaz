package co.edu.unbosque.util.exception;

import java.util.List;

public class ExceptionChecker {
	// Verificar si un número entero es negativo
	public static void checkNegativeNumber(int entero) throws NegativeNumberException {
		if (entero < 0) {
			throw new NegativeNumberException("El número no puede ser negativo.");
		}
	}

	//  Verificar si un número decimal es negativo
	public static void checkNegativeNumber(double numero) throws NegativeNumberException {
		if (numero < 0) {
			throw new NegativeNumberException("El número no puede ser negativo.");
		}
	}

	//  Verificar si una lista está vacía
	public static void checkEmptyList(List<?> lista) throws EmptyListException {
		if (lista == null || lista.isEmpty()) {
			throw new EmptyListException("La lista está vacía.");
		}
	}

	// Verificar si una opción está dentro del rango válido
	public static void checkValidOption(int opcion, int min, int max) throws InvalidOptionException {
		if (opcion < min || opcion > max) {
			throw new InvalidOptionException("Opción inválida. Debe estar entre " + min + " y " + max);
		}
	}

	//  Verificar si un String está vacío o nulo (versión básica)
	public static void checkEmptyString(String texto) throws EmptyStringException {
		if (texto == null || texto.trim().isEmpty()) {
			throw new EmptyStringException("El texto no puede estar vacío.");
		}
	}

	//  Verificar si un String está vacío o nulo (versión con mensaje
	// personalizado)
	public static void checkEmptyString(String texto, String mensajeError) throws EmptyStringException {
		if (texto == null || texto.trim().isEmpty()) {
			throw new EmptyStringException(mensajeError);
		}
	}

	//  Verificar si un objeto es nulo
	public static void checkNotNull(Object objeto) throws NullValueException {
		if (objeto == null) {
			throw new NullValueException("El valor no puede ser nulo.");
		}
	}
}
