package ru.nsu.fit.hmtl.core.typesystem.types;

import java.util.Collections;
import java.util.List;

/**
 * Generic type replacing varying types.
 */
public class GenericType extends Type {

	private final int id;

	public GenericType(int id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return "GT_" + id;
	}

	@Override
	public boolean isSubstitutable() {
		return false;
	}

	@Override
	public boolean isComplex() {
		return false;
	}

	@Override
	public List<Type> getInternalTypes() {
		return Collections.emptyList();
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Type;
	}

}
