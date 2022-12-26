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

public class Len extends Function {
	Type arg;

	{
		arg = BasicUtils.getGeneric();
		arg = new ListType(arg);
		TypeTable.getInstance().registerFinalType(arg);
		type = new ApplicationType(arg, BasicUtils.getInt());
		TypeTable.getInstance().registerFinalType(type);
	}

	@Override
	public Expression eval(ExecutionContext ctx) {
		ListObject lo = (ListObject) applied.get(0);
		return new BasicObject(lo.getData().size() - lo.getPos(), BasicUtils.getInt());
	}

	@Override
	public List<Type> getArgTypes() {
		return List.of(arg);
	}

	@Override
	public String toString() {
		return "len";
	}
}
