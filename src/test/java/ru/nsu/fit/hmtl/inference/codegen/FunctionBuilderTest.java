package ru.nsu.fit.hmtl.inference.codegen;

import org.burningwave.core.classes.Constructors;
import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.hmtl.codegen.builder.FunctionBuilder;
import ru.nsu.fit.hmtl.core.Applicable;
import ru.nsu.fit.hmtl.core.ApplicableWrapper;
import ru.nsu.fit.hmtl.core.TypedObject;
import ru.nsu.fit.hmtl.core.stl.ApplicableAdd;
import ru.nsu.fit.hmtl.inference.typesystem.types.BasicType;
import ru.nsu.fit.hmtl.inference.typesystem.types.BasicTypeBuilder;
import ru.nsu.fit.hmtl.inference.typesystem.types.Type;

public class FunctionBuilderTest {

	@Test
	public void simpleBuildTest() {
		FunctionBuilder builder = new FunctionBuilder();

		int r = builder.apply(0, 1);
		int s = builder.apply(r, 1);
		builder.callReturn(s);

		var kek = builder.make();

		ApplicableWrapper sumWrapper = new ApplicableWrapper.Builder().setCore(new ApplicableAdd()).build();
		Applicable applicable = Constructors.create().newInstanceOf(kek);
		ApplicableWrapper doubleWrapper = new ApplicableWrapper.Builder().setCore(applicable).build();

		Type t = new BasicTypeBuilder().setName(":Int").build();
		ApplicableWrapper variable = ApplicableWrapper.fromTypedObject(new TypedObject(3, t));

		var tmp1 = doubleWrapper.apply(sumWrapper);
		var tmp2 = tmp1.apply(variable);

		var x = tmp2.eval();

		int value = (Integer)x.getValue();

		Assert.assertEquals(6, value);
	}
}
