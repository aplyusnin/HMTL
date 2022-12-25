package ru.nsu.fit.hmtl.core.lang.list;

import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.ApplicationType;
import ru.nsu.fit.hmtl.core.typesystem.types.ListType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;
import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.lang.Function;
import ru.nsu.fit.hmtl.core.lang.ListObject;

import java.util.List;

public class Tail extends Function {

	Type arg;

	{
		arg = TypeTable.getInstance().createGenericType();
		arg = new ListType(arg);
		TypeTable.getInstance().registerFinalType(arg);
		type = new ApplicationType(arg, arg);
		TypeTable.getInstance().registerFinalType(type);
	}

	@Override
	public Expression eval() {
		ListObject lo = (ListObject) applied.get(0);
		ListObject copy = (ListObject) lo.deepCopy();
		copy.inc();
		return copy;
	}

	@Override
	public List<Type> getArgTypes() {
		return List.of(arg);
	}

	@Override
	public String toString() {
		return "tail";
	}

}