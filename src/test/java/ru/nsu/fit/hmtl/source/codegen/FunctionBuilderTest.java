package ru.nsu.fit.hmtl.source.codegen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.ExecutionContextImpl;
import ru.nsu.fit.hmtl.core.stl.StlExecutionContext;
import ru.nsu.fit.hmtl.source.codegen.builders.FunctionBuilder;

public class FunctionBuilderTest {

	@Test
	public void simpleBuildTest() {

		ExecutionContext ctx0 = StlExecutionContext.getInstance();

		ExecutionContextImpl ctx1 = new ExecutionContextImpl(ctx0);
		ctx1.setValue("a", null);
		ExecutionContextImpl ctx2 = new ExecutionContextImpl(ctx1);
		ctx2.setValue("b", null);

		FunctionBuilder builder = new FunctionBuilder(ctx2);

		int r = builder.apply("a", "b");
		int s = builder.apply(r, "b");
		builder.callReturn(s);

		var kek = builder.make();
		Assertions.assertTrue(ClassComparisonUtils.compareFiles("target/classes/ru/nsu/fit/hmtl/core/generated/Applicable1.java", "src/test/resources/generated0.java"));
	}
}
