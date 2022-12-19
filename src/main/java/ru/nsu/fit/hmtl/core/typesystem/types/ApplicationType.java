package ru.nsu.fit.hmtl.core.typesystem.types;

import java.util.List;

public class ApplicationType extends Type {

	private Type lhs, rhs;

	public ApplicationType(Type lhs, Type rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	public Type getLhs() {
		return lhs;
	}

	public Type getRhs() {
		return rhs;
	}

	public void setLhs(Type lhs) {
		this.lhs = lhs;
	}

	public void setRhs(Type rhs) {
		this.rhs = rhs;
	}

	@Override
	public String getName() {
		return "(" + lhs.getName() + "->" + rhs.getName() + ")";
	}

	@Override
	public boolean isSubstitutable() {
		return false;
	}

	@Override
	public boolean isComplex() {
		return true;
	}

	@Override
	public List<Type> getInternalTypes() {
		return List.of(lhs, rhs);
	}

}
