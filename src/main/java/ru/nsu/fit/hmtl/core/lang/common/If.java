package ru.nsu.fit.hmtl.core.lang.common;

import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.lang.BasicObject;
import ru.nsu.fit.hmtl.core.lang.BasicUtils;
import ru.nsu.fit.hmtl.core.lang.Function;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.ApplicationType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.List;

public class If extends Function {

	private final Type arg;
	{
		arg = BasicUtils.getGeneric();
		Type t1 = new ApplicationType(arg, arg);
		TypeTable.getInstance().registerFinalType(t1);
		t1 = new ApplicationType(arg, t1);
		TypeTable.getInstance().registerFinalType(t1);
		type = new ApplicationType(BasicUtils.getBool(), t1);
		TypeTable.getInstance().registerFinalType(type);
	}

	@Override
	public Expression eval(ExecutionContext ctx) {
		if (applied.size() < 3) return this;

		BasicObject val = (BasicObject) applied.get(0).eval(ctx);

		if ((Boolean) val.getValue()) {
			return applied.get(1).eval(ctx);
		} else {
			return applied.get(2).eval(ctx);
		}
	}

	@Override
	public List<Type> getArgTypes() {
		return List.of(BasicUtils.getBool(), arg, arg, arg);
	}

	@Override
	public String toString() {
		return "if";
	}
}
