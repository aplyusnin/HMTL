package ru.nsu.fit.hmtl.inference.context;

import ru.nsu.fit.hmtl.inference.types.Type;

/** An interface for representing Context of Hindley-Milner type system. */
public interface Context {

	/**
	 * Tries to get type of named variable
	 * @param name of variable
	 * @return type of variable
	 * @throws ContextException if variable doesn't present in context
	 */
	Type getType(String name) throws ContextException;

	/**
	 * Adds new names constant into context
	 * @param name of variable
	 * @param t type of variable
	 * @param allowRewrite existing types
	 * @throws ContextException if tries to rewrite existing constant's type having no allowance
	 */
	void addType(String name, Type t, boolean allowRewrite) throws ContextException;
}
