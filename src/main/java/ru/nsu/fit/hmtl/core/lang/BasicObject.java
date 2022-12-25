package ru.nsu.fit.hmtl.core.lang;

import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.typesystem.types.BasicType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;
import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.misc.Preconditions;

import java.util.Objects;

/**
 * Object of basic type
 */
public class BasicObject implements Expression {

	private final Object value;
	private final BasicType type;

	public BasicObject(Object value, Type type) {
		Preconditions.checkState(type instanceof BasicType);
		Preconditions.checkState(value.getClass().equals(((BasicType) type).getJavaClass()));
		this.value = value;
		this.type = (BasicType) type;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public Expression apply(Expression other) {
		return Expression.super.apply(other);
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public boolean equals(Expression other) {
		if (this == other) return true;
		if (other == null || getClass() != other.getClass()) return false;
		BasicObject that = (BasicObject) other;
		return type.getName().equals(that.type.getName()) && Objects.equals(value, that.value);
	}

	@Override
	public Expression deepCopy() {
		return this;
	}

	@Override
	public String toString() {
		return "BasicObject{" +
				"value=" + value +
				", type=" + type +
				'}';
	}

}
