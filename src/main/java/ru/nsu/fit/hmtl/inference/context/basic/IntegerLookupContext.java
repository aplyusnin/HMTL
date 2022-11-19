package ru.nsu.fit.hmtl.inference.context.basic;

import ru.nsu.fit.hmtl.inference.context.Context;
import ru.nsu.fit.hmtl.inference.context.ContextException;

public class IntegerLookupContext implements Context {

	@Override
	public int getTypeId(String name) throws ContextException {
		return 0;
	}

	@Override
	public void bindTypeId(String name, int typeId, boolean allowRewrite) throws ContextException
	{

	}

	@Override
	public void updateType(String name, int typeId) throws ContextException
	{

	}

}
