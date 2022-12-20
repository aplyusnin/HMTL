package ru.nsu.fit.hmtl.core;

/**
 * An interface for the function that can be called.
 */
public interface Applicable {

	/**
	 * Evaluates function.
	 * @param ctx - context of the function
	 * @return - result of the function
	 */
	TypedObject eval(ExecutionContext ctx);
}
