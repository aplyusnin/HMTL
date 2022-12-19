package ru.nsu.fit.hmtl.core.typesystem.types;

import java.util.List;

/**
 * Interface for representing Types;
 */
public abstract class Type {

	public Type() {
	}

	public abstract String getName();

	public abstract boolean isSubstitutable();

	public abstract boolean isComplex();

	public abstract List<Type> getInternalTypes();
}
