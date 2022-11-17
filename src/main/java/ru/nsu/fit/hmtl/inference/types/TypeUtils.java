package ru.nsu.fit.hmtl.inference.types;

import ru.nsu.fit.hmtl.inference.context.Context;

public class TypeUtils {

	/**
	 * Create an abstraction of types.
	 * @param left - left-hand side
	 * @param right - right-hand side
	 * @return new Type equals to lhs -> rhs
	 */
	public static Type abstraction(Type left, Type right) {
		return new DefaultTypeBuilder().setLeftHandSide(left).setRightHandSide(right).build();
	}

	/**
	 * Applies type t to the init type.
	 * @param init - initial type
	 * @param t - applied type
	 * @throws TypeInferenceException - if t doesn't match init's lhs
	 */
	public static Type application(Type init, Type t) throws TypeInferenceException {
		if (!substitutable(init.getLeftHandSide(), t)) {
			throw new TypeInferenceException("Type mismatch during an application");
		}
		return init.getRightHandSide();
	}


	/**
	 * Check if t1 can be substituted by t2
	 * @param t1 - type to be substituted
	 * @param t2 - type for substitution
	 * @return if t1 can be substituted with t2
	 */
	public static boolean substitutable(Type t1, Type t2) {
		// if t1 cannot be substituted or t2 cannot substitute
		if (!t1.isSubstitutable() || !t2.canSubstitute()) {
			return false;
		}
		// if t1 is of an applicative form
		if (t1.isComplex()) {
			// t2 should also be
			if (t2.isComplex()) {
				// check if both sides are match
				return substitutable(t1.getLeftHandSide(), t2.getLeftHandSide()) && substitutable(t1.getRightHandSide(), t2.getRightHandSide());
			} else {
				return false;
			}
		}
		// otherwise, t1 can always be changed to t2
		return true;
	}
}
