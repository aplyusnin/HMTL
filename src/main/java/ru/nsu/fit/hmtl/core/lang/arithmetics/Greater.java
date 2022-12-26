package ru.nsu.fit.hmtl.core.lang.arithmetics;

import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.lang.BasicObject;
import ru.nsu.fit.hmtl.core.lang.BasicUtils;
import ru.nsu.fit.hmtl.core.lang.Function;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.ApplicationType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.List;

public class Greater extends Function {

	{
		Type t1 = new ApplicationType(BasicUtils.getInt(), BasicUtils.getBool());
		TypeTable.getInstance().registerFinalType(t1);
		type = new ApplicationType(BasicUtils.getInt(), t1);
		TypeTable.getInstance().registerFinalType(type);
	}

	@Override
	public Expression eval(ExecutionContext ctx) {
		if (applied.size() < 2) return this;
		BasicObject lhs = (BasicObject) applied.get(0).eval(ctx);
		BasicObject rhs = (BasicObject) applied.get(1).eval(ctx);
		return new BasicObject((Integer) lhs.getValue() > (Integer) rhs.getValue(), TypeTable.getInstance().getType("BT_Bool"));
	}

	@Override
	public List<Type> getArgTypes() {
		return List.of(BasicUtils.getInt(), BasicUtils.getInt());
	}

	@Override
	public String toString() {
		return ">";
	}

}
