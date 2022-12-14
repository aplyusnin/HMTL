package ru.nsu.fit.hmtl.core;

/**
 * An interface for the function that can be called.
 */
public interface Applicable {
	TypedObject apply(WrapperList args);

	// ArgsInfo getArgsInfo();
}
