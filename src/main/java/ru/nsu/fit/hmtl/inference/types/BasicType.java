package ru.nsu.fit.hmtl.inference.types;

public class BasicType extends Type {
	private final String name;

	public BasicType(String name) {
		this(name, EmptyType.getInstance(), EmptyType.getInstance());
	}

	public BasicType(String name, Type lhs, Type rhs) {
		super(lhs, rhs);
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isSubstitutable() {
		return false;
	}

}
