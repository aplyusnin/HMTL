package ru.nsu.fit.hmtl.core.typesystem.types;

import java.util.Collections;
import java.util.List;

/**
 * Varying type assigned during type inference.
 */
public class VaryingType extends Type {

	private final int id;

	public VaryingType(int id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return "VT_" + id;
	}

	@Override
	public boolean isSubstitutable() {
		return true;
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
