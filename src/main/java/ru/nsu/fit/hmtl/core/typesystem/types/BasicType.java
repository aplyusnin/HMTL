package ru.nsu.fit.hmtl.core.typesystem.types;

import java.util.Collections;
import java.util.List;

/**
 *  Class representing basic type.
 */
public class BasicType extends Type {

	private final String name;

	private final Class<?> javaClass;

	public BasicType(String name, Class<?> javaClass) {
		this.name = "BT_" + name;
		this.javaClass = javaClass;
	}

	@Override
	public String getName() {
		return name;
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

	public Class<?> getJavaClass() {
		return javaClass;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BasicType)) return false;

		BasicType basicType = (BasicType) o;

		return name.equals(basicType.name);
	}
}
