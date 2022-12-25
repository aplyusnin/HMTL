package ru.nsu.fit.hmtl.core;

import ru.nsu.fit.hmtl.core.typesystem.types.Type;

public class InvalidApplicationException extends RuntimeException {
	private final String name1;
	private final String name2;

	private final Type type1;
	private final Type type2;

	public InvalidApplicationException(String name1, String name2, Type t1, Type t2) {
		this.name1 = name1;
		this.type1 = t1;
		this.name2 = name2;
		this.type2 = t2;
	}

	@Override
	public String toString() {
		return "InvalidApplicationException{" +
				"name1='" + name1 + '\'' +
				", type1=" + type1.getName() +
				", name2='" + name2 + '\'' +
				", type2=" + type2.getName() +
				'}';
	}

	@Override
	public String getMessage() {
		return "Expression " + name2 + " of type " + type2.getName() + " cannot be applied to " + name1 + " of type: " + type1;
	}

}
