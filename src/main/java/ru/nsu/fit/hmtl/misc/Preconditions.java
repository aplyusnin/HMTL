package ru.nsu.fit.hmtl.misc;

/** Class for runtime assertions. */
public class Preconditions {

	public static void checkNotNull(Object o, String message) {
		if (o == null) {
			throw new RuntimeException(message);
		}
	}

	public static void checkNotNull(Object o) {
		if (o == null) {
			throw new RuntimeException();
		}
	}

	public static void checkState(boolean condition, String message) {
		if (!condition) {
			throw new RuntimeException(message);
		}
	}

	public static void checkState(boolean condition) {
		if (!condition) {
			throw new RuntimeException();
		}
	}
}
