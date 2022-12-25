package ru.nsu.fit.hmtl.core;

import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.Objects;

public interface Expression {

	default Expression eval() {
		return this;
	}

	default Expression apply(Expression other) {
		throw new InvalidApplicationException(this.toString(), other.toString(), this.getType(), other.getType());
	}

	Type getType();

	default void setType(Type type) {
	}

	default boolean equals(Expression other) {
		return Objects.equals(this, other);
	}

	Expression deepCopy();


}
