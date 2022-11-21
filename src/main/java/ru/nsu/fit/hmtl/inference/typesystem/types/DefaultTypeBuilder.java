package ru.nsu.fit.hmtl.inference.typesystem.types;

import ru.nsu.fit.hmtl.inference.typesystem.unification.ComplexUnificationStrategy;

/**
 * Default builder for {@link Type}.
 */
public class DefaultTypeBuilder implements TypeBuilder {

	private int id = -1;
	private Type lhs = EmptyType.getInstance();
	private Type rhs = EmptyType.getInstance();

	public DefaultTypeBuilder() {
	}

	@Override
	public DefaultTypeBuilder setId(int id) {
		this.id = id;
		return this;
	}

	@Override
	public DefaultTypeBuilder setRightHandSide(Type rhs) {
		this.rhs = rhs;
		return this;
	}

	@Override
	public DefaultTypeBuilder setLeftHandSide(Type lhs) {
		this.lhs = lhs;
		return this;
	}

	@Override
	public Type getRightHandSide() {
		return rhs;
	}

	@Override
	public Type getLeftHandSide() {
		return lhs;
	}

	@Override
	public Type build() {
		return new Type(id, lhs, rhs, ComplexUnificationStrategy.getInstance());
	}

}
