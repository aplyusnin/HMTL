package ru.nsu.fit.hmtl.inference.context.basic.typed;

import ru.nsu.fit.hmtl.inference.context.Context;
import ru.nsu.fit.hmtl.inference.context.ContextException;

import java.util.Optional;

public abstract class TypedContext implements Context {

	private final Context parentContext;

	public TypedContext(Context parent) {
		parentContext = parent;
	}

	public abstract Optional<String> extractType(String constantName);

	@Override
	public int getTypeId(String name) throws ContextException {
		Optional<String> value = extractType(name);
		return parentContext.getTypeId(value.orElse(name));
	}

	@Override
	public void bindTypeId(String name, int typeId, boolean allowRewrite)  throws ContextException{
		throw new ContextException("TypedContext context cannot be modified");
	}

	@Override
	public void updateType(String name, int typeId) throws ContextException {
		throw new ContextException("TypedContext context cannot be modified");
	}
}
