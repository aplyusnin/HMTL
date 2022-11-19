package ru.nsu.fit.hmtl.inference.typesystem.types;

import ru.nsu.fit.hmtl.inference.typesystem.unification.UnificationStrategy;

/**
 * A basic class for typing.
 *
 * Constants and variables are considered as 0-arity function f() = x.
 */
public class Type {

	// Index of Type from the type table
	private final int id;

	// link for composite ids
	private final Type lhs, rhs;

	// unification strategy for current type
	private final UnificationStrategy unificationStrategy;

	protected Type(int id, Type lhs, Type rhs, UnificationStrategy unificationStrategy) {
		this.id = id;
		this.lhs = lhs;
		this.rhs = rhs;
		this.unificationStrategy = unificationStrategy;
	}

	/**
	 * Returns if type can substitute other type.
	 */
	public boolean canSubstitute() {
		return true;
	}

	public boolean isComplex() {
		return lhs.getId() != 0;
	}

	public int getId() {
		return id;
	}

	public Type getLhs() {
		return lhs;
	}

	public Type getRhs() {
		return rhs;
	}

	public UnificationStrategy getUnificationStrategy() {
		return unificationStrategy;
	}

	public String getName() {
		return "(" + lhs.getName() + "->" + rhs.getName() + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Type)) return false;

		Type type = (Type) o;

		return id == type.id;
	}
}
