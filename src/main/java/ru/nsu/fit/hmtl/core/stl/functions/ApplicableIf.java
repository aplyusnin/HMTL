package ru.nsu.fit.hmtl.core.stl.functions;

import ru.nsu.fit.hmtl.core.Applicable;
import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.TypedObject;

public class ApplicableIf implements Applicable {

	@Override
	public Expression eval(ExecutionContext ctx)
	{
		if ((Boolean) ctx.lookup("a").eval().getValue().getValue()) {
			Expression expr = ctx.lookup("b").eval();
			return Expression.fromTypedObject(new TypedObject(expr.getValue(), expr.getType()), ctx);
		} else {
			Expression expr = ctx.lookup("c").eval();
			return Expression.fromTypedObject(new TypedObject(expr.getValue(), expr.getType()), ctx);
		}
	}

}
