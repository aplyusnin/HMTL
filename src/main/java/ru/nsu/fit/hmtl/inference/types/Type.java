package ru.nsu.fit.hmtl.inference.types;

import java.util.Locale;

/**
 * A basic class for typing.
 *
 * Constants and variables are considered as 0-arity function f() = x.
 */
public class Type {

	private final Type leftHandSide;
	private final Type rightHandSide;

	protected Type(Type left, Type right) {
		leftHandSide = left;
		rightHandSide = right;
	}

	/**
	 * Type is complex if is an abstraction over non-empty type.
	 */
	public boolean isComplex() {
		return leftHandSide.isSubstitutable() || rightHandSide.canSubstitute();
	}

	public Type getLeftHandSide() {
		return leftHandSide;
	}

	public Type getRightHandSide() {
		return rightHandSide;
	}

	/**
	 * Returns if this type can be substituted by any other types.
	 */
	public boolean isSubstitutable() {
		return true;
	}

	/**
	 * Returns if this type can substitute any other types.
	 */
	public boolean canSubstitute() {
		return true;
	}

	/**
	 * Get name of current type.
	 */
	public String getName() {
		if (isComplex()) {
			return "(" + leftHandSide.getName() + "->" + rightHandSide.getName() + ")";
		}
		return "VarT";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Type)) return false;

		Type type = (Type) o;

		return getName().equals(type.getName());
	}

	public interface TypeBuilder {
		TypeBuilder setRightHandSide(Type rhs);
		TypeBuilder setLeftHandSide(Type lhs);

		Type getRightHandSide();
		Type getLeftHandSide();

		Type build();
	}
}
