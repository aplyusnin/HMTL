package ru.nsu.fit.hmtl.core.typesystem.context;

import ru.nsu.fit.hmtl.core.typesystem.types.GenericType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.HashMap;
import java.util.Map;

public class TypeContextImpl implements TypeContext {

	private final Map<String, Type> storage;

	private final TypeContext parent;

	public TypeContextImpl(TypeContext parent) {
		this.parent = parent;
		this.storage = new HashMap<>();
	}

	@Override
	public Type lookup(String name) {
		if (storage.containsKey(name)) return storage.get(name);
		return parent.lookup(name);
	}

	@Override
	public TypeContext createSubContext() {
		return new TypeContextImpl(this);
	}

	@Override
	public void setType(String name, Type type) {
		storage.put(name, type);
	}

}
