package ru.nsu.fit.hmtl.core.typesystem.context;

import ru.nsu.fit.hmtl.core.typesystem.types.Type;

public interface TypeContext {
	Type lookup(String name);

	TypeContext createSubContext();

	void setType(String name, Type type);
}
