package ru.nsu.fit.hmtl.inference.typesystem.types;

/**
 * A class representing List elements sharing the same type.
 *
 * It can be represented as () -> [x]
 */
public class ListType extends Type {

	protected ListType(Type left, Type right) {
		super(-1, left, right, null);
	}


	@Override
	public boolean isComplex() {
		return false;
	}

	@Override
	public boolean canSubstitute() {
		return getRhs().canSubstitute();
	}

}
