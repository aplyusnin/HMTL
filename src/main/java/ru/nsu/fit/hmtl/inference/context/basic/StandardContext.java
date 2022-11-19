package ru.nsu.fit.hmtl.inference.context.basic;

import ru.nsu.fit.hmtl.inference.context.Context;
import ru.nsu.fit.hmtl.inference.context.ContextException;
import ru.nsu.fit.hmtl.inference.typesystem.types.Type;

import java.util.HashMap;
import java.util.Map;

public class StandardContext implements Context {
	Map<String, Integer> namedTypes = new HashMap<>();

	@Override
	public int getTypeId(String name) throws ContextException {
		if (namedTypes.containsKey(name)) {
			return namedTypes.get(name);
		}
		throw new ContextException("Variable " + name + " doesn't present in context");
	}

	@Override
	public void bindTypeId(String name, int typeId, boolean allowRewrite)  throws ContextException{
		if (!namedTypes.containsKey(name)) {
			namedTypes.put(name, typeId);
		}
		else if (allowRewrite) {
			namedTypes.put(name, typeId);
		}
		else {
			throw new ContextException("Trying to rewrite existing variable");
		}
	}

	@Override
	public void updateType(String name, int typeId) throws ContextException {
		throw new ContextException("Basic context cannot be modified");
	}

}
