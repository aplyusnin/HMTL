package ru.nsu.fit.hmtl.inference.context.basic;

import ru.nsu.fit.hmtl.inference.context.Context;
import ru.nsu.fit.hmtl.inference.context.ContextException;

public class EmptyContext implements Context {

	@Override
	public int getTypeId(String name) throws ContextException {
		throw new ContextException("Cannot access empty context");
	}

	@Override
	public void bindTypeId(String name, int typeId, boolean allowRewrite) throws ContextException {
		throw new ContextException("Cannot modify empty context");
	}

	@Override
	public void updateType(String name, int typeId) throws ContextException {
		throw new ContextException("Cannot modify empty context");
	}

}
