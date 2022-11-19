package ru.nsu.fit.hmtl.inference.typesystem.unification;

import ru.nsu.fit.hmtl.inference.typesystem.types.Type;
import ru.nsu.fit.hmtl.inference.typesystem.TypeInferenceException;

import java.util.Optional;

/**
 * Strategy for unification one type with another.
 */
public interface UnificationStrategy {

	/**
	 * Unify types.
	 * @param owner - basic type of unification
	 * @param other - second type for unification
	 */
	Optional<Type> unifyWith(Type owner, Type other) throws TypeInferenceException;

	/**
	 * Check if one type can be substituted by another
	 * @param t1 - to be substituted
	 * @param t2 - to substitute
	 * @return if t1 is substituted by t2.
	 */
	boolean isSubstitutableWith(Type t1, Type t2);
}
