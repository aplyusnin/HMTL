package ru.nsu.fit.hmtl.core.lang.bytes;

import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.lang.BasicObject;
import ru.nsu.fit.hmtl.core.lang.BasicUtils;
import ru.nsu.fit.hmtl.core.lang.Function;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.ApplicationType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.List;

public class And extends Function {

	{
		Type t1 = new ApplicationType(BasicUtils.getByte(), BasicUtils.getByte());
		TypeTable.getInstance().registerFinalType(t1);
		type = new ApplicationType(BasicUtils.getByte(), t1);
		TypeTable.getInstance().registerFinalType(type);
	}

	@Override
	public Expression evalInternal(ExecutionContext ctx) {
		BasicObject lhs = (BasicObject) applied.get(0).eval(ctx);
		BasicObject rhs = (BasicObject) applied.get(1).eval(ctx);
		return BasicUtils.createNumeric((Byte) lhs.getValue() & (Byte) rhs.getValue());
	}

	@Override
	public List<Type> getArgTypes() {
		return List.of(BasicUtils.getByte(), BasicUtils.getByte());
	}

	@Override
	public String toString() {
		return "&";
	}
}
