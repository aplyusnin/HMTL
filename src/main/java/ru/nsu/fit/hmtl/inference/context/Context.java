package ru.nsu.fit.hmtl.inference.context;

/** An interface for representing Context of Hindley-Milner type system. */
public interface Context {

	/**
	 * Tries to get type id of named variable
	 * @param name of variable
	 * @return type of variable
	 * @throws ContextException if variable doesn't present in context
	 */
	int getTypeId(String name) throws ContextException;

	/**
	 * Binds type id to the variable name
	 * @param name of variable
	 * @param typeId index of type of variable
	 * @param allowRewrite existing types
	 * @throws ContextException if tries to rewrite existing constant's type having no allowance
	 */
	void bindTypeId(String name, int typeId, boolean allowRewrite) throws ContextException;


	/**
	 * Update varying type with another type
	 * @param name of constant
	 * @param typeId - new type index
	 */
	void updateType(String name, int typeId) throws ContextException;
}
