package ru.nsu.fit.hmtl.core;

import ru.nsu.fit.hmtl.core.typesystem.types.Type;

/**
 * A value with assigned type to it.
 */
public class TypedObject {
	private final Object value;
	private final Type type;

	public TypedObject(Object value, Type type) {
		this.value = value;
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public Type getType() {
		return type;
	}

	public static TypedObject functional(Type t) {
		return new TypedObject("Func", t);
	}
}
