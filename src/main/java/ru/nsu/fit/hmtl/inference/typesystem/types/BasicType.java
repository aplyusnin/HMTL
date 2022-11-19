package ru.nsu.fit.hmtl.inference.typesystem.types;

import ru.nsu.fit.hmtl.inference.typesystem.unification.UnificationStrategy;

public class BasicType extends Type {
	private final String name;

	public BasicType(String name, int id, UnificationStrategy unificationStrategy) {
		this(name, id, EmptyType.getInstance(), EmptyType.getInstance(), unificationStrategy);
	}

	public BasicType(String name, int id, Type lhs, Type rhs, UnificationStrategy unificationStrategy) {
		super(id, lhs, rhs, unificationStrategy);
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
