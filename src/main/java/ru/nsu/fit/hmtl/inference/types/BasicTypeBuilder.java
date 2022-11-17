package ru.nsu.fit.hmtl.inference.types;

import java.util.UUID;

public class BasicTypeBuilder implements Type.TypeBuilder {

	// Set some random name by default
	private String name = UUID.randomUUID().toString();

	@Override
	public BasicTypeBuilder setRightHandSide(Type rhs) {
		throw new TypeInferenceException("Cannot make basic type applicable");
	}

	@Override
	public BasicTypeBuilder setLeftHandSide(Type lhs) {
		throw new TypeInferenceException("Cannot make basic type applicable");
	}

	public BasicTypeBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public String getName() {
		return name;
	}

	@Override
	public Type getRightHandSide() {
		throw new TypeInferenceException("Cannot make basic type applicable");
	}

	@Override
	public Type getLeftHandSide() {
		throw new TypeInferenceException("Cannot make basic type applicable");
	}

	@Override
	public Type build() {
		return new BasicType(name);
	}

}
