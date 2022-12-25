package ru.nsu.fit.hmtl.core.lang.list;

import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.lang.BasicUtils;
import ru.nsu.fit.hmtl.core.lang.Function;
import ru.nsu.fit.hmtl.core.lang.ListObject;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.ApplicationType;
import ru.nsu.fit.hmtl.core.typesystem.types.ListType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.Collections;
import java.util.List;

public class Append  extends Function {

	private final Type arg1, arg2;
	{
		arg2 = BasicUtils.getGeneric();
		arg1 = new ListType(arg2);
		TypeTable.getInstance().registerFinalType(arg1);
		type = new ApplicationType(arg2, arg1);
		TypeTable.getInstance().registerFinalType(type);
		type = new ApplicationType(arg1, type);
		TypeTable.getInstance().registerFinalType(type);
	}

	@Override
	public Expression eval() {
		if (applied.size() < 2) return this;
		ListObject lo = (ListObject) (applied.get(0)).deepCopy();
		lo.getData().add(applied.get(1));
		return lo;
	}

	@Override
	public List<Type> getArgTypes() {
		return Collections.emptyList();
	}

	@Override
	public String toString() {
		return "append";
	}
}
