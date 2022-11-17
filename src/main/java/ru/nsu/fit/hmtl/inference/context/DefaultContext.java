package ru.nsu.fit.hmtl.inference.context;

import ru.nsu.fit.hmtl.inference.types.Type;

import java.util.HashMap;
import java.util.Map;

public class DefaultContext implements Context {

	// parent context is used for lookup if no info found in current
	private final Context parentContext;

	private final Map<String, Type> namedTypes;

	public DefaultContext(Context parentContext) {
		this.parentContext = parentContext;
		this.namedTypes = new HashMap<>();
	}

	@Override
	public Type getType(String name) throws ContextException {
		if (namedTypes.containsKey(name)) return namedTypes.get(name);
		return parentContext.getType(name);
	}

	@Override
	public void addType(String name, Type t, boolean allowRewrite) throws ContextException {
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
