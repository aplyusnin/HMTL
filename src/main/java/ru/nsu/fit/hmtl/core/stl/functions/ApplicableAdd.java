package ru.nsu.fit.hmtl.core.stl.functions;

import ru.nsu.fit.hmtl.core.*;
import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;

public class ApplicableAdd implements Applicable {

	@Override
	public Expression eval(ExecutionContext ctx) {
		return Expression.fromTypedObject(new TypedObject((Integer) ctx.lookup("a").eval().getValue().getValue() + (Integer) ctx.lookup("b").eval().getValue().getValue(), TypeTable.getInstance().getType("BT_Numeric")), ctx);
	}

}
