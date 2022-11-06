package ru.nsu.fit.hmtl.inference;

import org.junit.Assert;
import org.junit.Test;

public class TypeUtilsTest {

	@Test
	public void testConstantTypeFullName() {
		ConstantType type = new ConstantType("Subtype");
		Assert.assertEquals("Subtype", TypeUtils.getFullName(type));
		ConstantType parent = new ConstantType("ParentType");
		type.inherit(parent);
		Assert.assertEquals("ParentType.Subtype", TypeUtils.getFullName(type));
	}

	@Test
	public void testFunctionalTypeFullName() {
		ConstantType type1 = new ConstantType("Type1");
		ConstantType type2 = new ConstantType("Type2");
		ConstantType result = new ConstantType("Type3");
		FunctionalType type = new FunctionalType(result, type1, type2);
		Assert.assertEquals("Type1->Type2->Type3", TypeUtils.getFullName(type));
		ConstantType parentType = new ConstantType("ParentType");
		type1.inherit(parentType);
		result.inherit(parentType);
		Assert.assertEquals("ParentType.Type1->Type2->ParentType.Type3", TypeUtils.getFullName(type));
	}
}
