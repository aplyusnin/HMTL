package ru.nsu.fit.hmtl.inference.typesystem.types;

import ru.nsu.fit.hmtl.inference.typesystem.TypeInferenceException;
import ru.nsu.fit.hmtl.inference.typesystem.unification.VariableUnificationStrategy;

/**
 * Builder for {@link VariableType}.
 */
public class VariableTypeBuilder implements TypeBuilder {

	private int id = -1;
	private final Type lhs = EmptyType.getInstance();
	private Type rhs;

	@Override
	public TypeBuilder setId(int id) {
		this.id = id;
		return this;
	}

	@Override
	public TypeBuilder setRightHandSide(Type rhs) {
		this.rhs = rhs;
		return this;
	}

	@Override
	public TypeBuilder setLeftHandSide(Type lhs) {
		throw new TypeInferenceException("Variable type should not be abstract over some variable");
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
		return new VariableType(id, lhs, rhs, VariableUnificationStrategy.getInstance());
	}

}
