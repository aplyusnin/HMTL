package ru.nsu.fit.hmtl.core.lang.list;

import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.lang.BasicObject;
import ru.nsu.fit.hmtl.core.lang.Function;
import ru.nsu.fit.hmtl.core.lang.ListObject;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.ApplicationType;
import ru.nsu.fit.hmtl.core.typesystem.types.BasicType;
import ru.nsu.fit.hmtl.core.typesystem.types.ListType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.List;

public class Head extends Function {

	private final Type arg;

	{
		type = TypeTable.getInstance().createGenericType();
		arg = new ListType(type);
		TypeTable.getInstance().registerFinalType(arg);
		type = new ApplicationType(arg, type);
		TypeTable.getInstance().registerFinalType(type);
	}

	@Override
	public Expression eval(ExecutionContext ctx) {
		ListObject object = ((ListObject) applied.get(0));
		Expression expr = object.getData().get(object.getPos());
		if (expr.getType() instanceof BasicType) {
			expr = new BasicObject(expr.eval(ctx), expr.getType());
		}
		return expr;
	}

	@Override
	public List<Type> getArgTypes() {
		return List.of(type);
	}

	@Override
	public String toString() {
		return "head";
	}
}