package ru.nsu.fit.hmtl.inference.types;

public class DefaultTypeBuilder implements Type.TypeBuilder {

	private Type lhs = EmptyType.getInstance();
	private Type rhs = EmptyType.getInstance();

	@Override
	public Type.TypeBuilder setRightHandSide(Type rhs) {
		this.rhs = rhs;
		return this;
	}

	@Override
	public Type.TypeBuilder setLeftHandSide(Type lhs) {
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
		return new Type(lhs, rhs);
	}

}
