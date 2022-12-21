package ru.nsu.fit.hmtl.core.typesystem.types;

import java.util.List;

public class ListType extends Type {

	private Type core;

	public ListType(Type core) {
		this.core = core;
	}

	public Type getCore() {
		return core;
	}

	public void setCore(Type core) {
		this.core = core;
	}

	@Override
	public String getName() {
		return "[" + core.getName() + "]";
	}

	@Override
	public boolean isSubstitutable() {
		return false;
	}

	@Override
	public boolean isComplex() {
		return true;
	}

	@Override
	public List<Type> getInternalTypes() {
		return List.of(core);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ListType)) return false;

		ListType listType = (ListType) o;

		return getCore().equals(listType.getCore());
	}
}
