package ru.nsu.fit.hmtl.core.typesystem.types;

import java.util.Collections;
import java.util.List;

/**
 *  Class representing basic type.
 */
public class BasicType extends Type {

	private final String name;

	private final String javaName;

	public BasicType(String name) {
		this(name, name);
	}

	public BasicType(String name, String javaName) {
		this.name = "BT_" + name;
		this.javaName = javaName;
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

	public String getJavaName() {
		return javaName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BasicType)) return false;

		BasicType basicType = (BasicType) o;

		return name.equals(basicType.name);
	}
}
