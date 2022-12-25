package ru.nsu.fit.hmtl.core;

import ru.nsu.fit.hmtl.core.typesystem.types.Type;

public class DummyExpression implements Expression {

	private final Type type;

	public DummyExpression(Type t) {
		this.type = t;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Expression deepCopy() {
		return new DummyExpression(type);
	}

}
