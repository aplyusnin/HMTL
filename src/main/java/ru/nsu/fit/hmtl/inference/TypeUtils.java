package ru.nsu.fit.hmtl.inference;

import java.util.Iterator;
import java.util.LinkedList;

public class TypeUtils {

	/** Function for creating full name of the constant type. */
	public static String getFullName(ConstantType type) {
		LinkedList<String> names = new LinkedList<>();
		names.add(type.getName());
		while (type.getParent().isPresent()) {
			type = type.getParent().get();
			names.add(type.getName());
		}
		StringBuilder builder = new StringBuilder();
		Iterator<String> iterator = names.descendingIterator();
		builder.append(iterator.next());
		while (iterator.hasNext())  {
			builder.append(".").append(iterator.next());
		}
		return builder.toString();
	}

	/** Function for creating full name of the functional type. */
	public static String getFullName(FunctionalType type) {
		StringBuilder builder = new StringBuilder();
		for (var t : type.getArgs()) {
			builder.append(getFullName(t)).append("->");
		}
		builder.append(getFullName(type.getResult()));

		return builder.toString();
	}
}
