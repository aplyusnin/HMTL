package ru.nsu.fit.hmtl.core;

import ru.nsu.fit.hmtl.inference.typesystem.types.Type;

/**
 * A final object with type assigned to it.
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

}
