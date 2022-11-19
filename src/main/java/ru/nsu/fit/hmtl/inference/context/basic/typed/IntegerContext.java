package ru.nsu.fit.hmtl.inference.context.basic.typed;

import ru.nsu.fit.hmtl.inference.context.Context;

import java.util.Optional;

public class IntegerContext extends TypedContext {

	public IntegerContext(Context parent) {
		super(parent);
	}

	@Override
	public Optional<String> extractType(String constantName) {
		try {
			Integer.parseInt(constantName);
			return Optional.of(":Int");
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}

}
