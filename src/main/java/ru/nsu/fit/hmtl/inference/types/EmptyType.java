package ru.nsu.fit.hmtl.inference.types;

/**
 * An empty type which cannot substitute or be substituted.
 */
public class EmptyType extends Type {

	private static final EmptyType instance = new EmptyType();

	private EmptyType() {
		super(null, null);
	}

	public static EmptyType getInstance() {
		return instance;
	}

	@Override
	public boolean isComplex() {
		return false;
	}

	@Override
	public boolean isSubstitutable() {
		return false;
	}

	@Override
	public boolean canSubstitute() {
		return false;
	}
	@Override
	public String getName() {
		return "";
	}

}
