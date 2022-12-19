package ru.nsu.fit.hmtl.core.typesystem.context;

import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.GenericType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

public class ParsingTypeContext implements TypeContext {

	@Override
	public Type lookup(String name) {
		if (name.equals("True") || name.equals("False")) return TypeTable.getInstance().getType("BT_Bool");
		try {
			Integer.parseInt(name);
			return TypeTable.getInstance().getType("BT_Numeric");
		} catch (Exception ignored) {
		}
		throw new RuntimeException("Unknown lex: " + name);
	}

	@Override
	public TypeContext createSubContext() {
		return null;
	}

	@Override
	public void setType(String name, Type type) {
	}
}
