package ru.nsu.fit.hmtl.core;

/**
 * Context with expressions.
 */
public interface ExecutionContext {

	Expression lookup(String name);

	boolean contains(String name);

	ExecutionContext createSubContext();

	ExecutionContext createCopy();

	void setValue(String name, Expression newValue);

	ExecutionContext getParent();
}
