package ru.nsu.fit.hmtl.inference.context.basic;

import ru.nsu.fit.hmtl.inference.context.Context;
import ru.nsu.fit.hmtl.inference.context.ContextException;
import ru.nsu.fit.hmtl.inference.types.Type;

import java.util.HashMap;
import java.util.Map;

public class BasicContext implements Context {
	Map<String, Type> namedTypes = new HashMap<>();

	@Override
	public Type getType(String name) throws ContextException {
		if (namedTypes.containsKey(name)) {
			return namedTypes.get(name);
		}
		throw new ContextException("Variable " + name + " doesn't present in context");
	}

	@Override
	public void addType(String name, Type t, boolean allowRewrite)  throws ContextException{
		if (!namedTypes.containsKey(name)) {
			namedTypes.put(name, t);
		}
		else if (allowRewrite) {
			namedTypes.put(name, t);
		}
		else {
			throw new ContextException("Trying to rewrite existing variable");
		}
	}
}
