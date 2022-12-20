package ru.nsu.fit.hmtl.core.stl.functions;

import ru.nsu.fit.hmtl.core.Applicable;
import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.TypedObject;

public class ApplicablePrint implements Applicable {

	@Override
	public Expression eval(ExecutionContext ctx) {
		System.out.println(ctx.lookup("a").eval().getValue());
		return ctx.lookup("a").eval();
	}

}
