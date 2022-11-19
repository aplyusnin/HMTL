package ru.nsu.fit.hmtl.inference.typesystem.types;

import ru.nsu.fit.hmtl.inference.typesystem.unification.UnificationStrategy;

public class VariableType extends Type {

	protected VariableType(int id, Type lhs, Type rhs, UnificationStrategy unificationStrategy) {
		super(id, lhs, rhs, unificationStrategy);
	}

	@Override
	public boolean canSubstitute() {
		return true;
	}

	@Override
	public boolean isComplex() {
		return false;
	}

	@Override
	public String getName() {
		return "T_" + getId();
	}

}
