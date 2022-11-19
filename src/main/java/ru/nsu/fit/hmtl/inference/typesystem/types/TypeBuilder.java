package ru.nsu.fit.hmtl.inference.typesystem.types;

public interface TypeBuilder {
	TypeBuilder setId(int id);
	TypeBuilder setRightHandSide(Type rhs);
	TypeBuilder setLeftHandSide(Type lhs);

	Type getRightHandSide();
	Type getLeftHandSide();

	Type build();
}
