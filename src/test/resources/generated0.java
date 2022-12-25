package ru.nsu.fit.hmtl.core.generated;

import java.lang.Override;
import ru.nsu.fit.hmtl.core.Applicable;
import ru.nsu.fit.hmtl.core.ExecutionContext;

public class Applicable1 implements Applicable { 

	@Override
	public Expression eval(
		ExecutionContext ctx
	) {
		Expression V_0 = (ctx.lookup("a")).apply((ctx.lookup("b"))); 
		Expression V_1 = V_0.apply((ctx.lookup("b"))); 
		return V_1.eval(); 
	} 

}