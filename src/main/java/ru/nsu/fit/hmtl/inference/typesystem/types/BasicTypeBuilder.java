package ru.nsu.fit.hmtl.inference.typesystem.types;

import ru.nsu.fit.hmtl.inference.typesystem.TypeInferenceException;
import ru.nsu.fit.hmtl.inference.typesystem.unification.ConstantUnificationStrategy;

import java.util.UUID;

public class BasicTypeBuilder implements TypeBuilder {

	// Set some random name by default
	private String name = UUID.randomUUID().toString();
	private int id = -1;

	@Override
	public TypeBuilder setId(int id) {
		this.id = id;
		return this;
	}

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
		return new BasicType(name, id, new ConstantUnificationStrategy());
	}

}
