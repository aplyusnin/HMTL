package ru.nsu.fit.hmtl.core.lang.list;

import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.lang.BasicObject;
import ru.nsu.fit.hmtl.core.lang.BasicUtils;
import ru.nsu.fit.hmtl.core.lang.Function;
import ru.nsu.fit.hmtl.core.lang.ListObject;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.ApplicationType;
import ru.nsu.fit.hmtl.core.typesystem.types.ListType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.List;

public class Wrap extends Function {
	Type arg;

	{
		arg = BasicUtils.getGeneric();
		type = new ListType(arg);
		TypeTable.getInstance().registerFinalType(type);
		type = new ApplicationType(arg, type);
		TypeTable.getInstance().registerFinalType(type);
	}

	@Override
	public Expression evalInternal(ExecutionContext ctx) {
		Expression res = applied.get(0).eval(ctx);
		ListObject lo = new ListObject(res.getType());
		lo.getData().add(res);
		return lo;
	}

	@Override
	public List<Type> getArgTypes() {
		return List.of(arg);
	}

	@Override
	public String toString() {
		return "wo";
	}
}
