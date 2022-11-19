package ru.nsu.fit.hmtl.inference.context.basic;

import ru.nsu.fit.hmtl.inference.context.Context;
import ru.nsu.fit.hmtl.inference.context.ContextException;

public class BasicContext implements Context {
	private static final BasicContext basicContext = new BasicContext();

	private BasicContext() {
	}

	@Override
	public int getTypeId(String name) throws ContextException {
		return 0;
	}

	@Override
	public void bindTypeId(String name, int typeId, boolean allowRewrite) throws ContextException {
		throw new ContextException("Cannot modify basic context.");
	}

	@Override
	public void updateType(String name, int typeId) throws ContextException {
		throw new ContextException("Cannot modify basic context.");
	}

}
