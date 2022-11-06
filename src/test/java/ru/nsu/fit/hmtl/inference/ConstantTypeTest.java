package ru.nsu.fit.hmtl.inference;

import org.junit.Assert;
import org.junit.Test;

public class ConstantTypeTest {

	@Test
	public void validInheritanceTest() {
		ConstantType type1 = new ConstantType("T0");
		ConstantType type2 = new ConstantType("T1");
		type1.inherit(type2);
		ConstantType type3 = new ConstantType("T2", type1);
		ConstantType type4 = new ConstantType("T2", type1);
		// check existence of parents
		Assert.assertTrue(type3.getParent().isPresent());
		Assert.assertTrue(type4.getParent().isPresent());
		Assert.assertTrue(type1.getParent().isPresent());
		Assert.assertTrue(type2.getParent().isEmpty());

		// check parents
		Assert.assertEquals(type1, type3.getParent().get());
		Assert.assertEquals(type1, type4.getParent().get());
		Assert.assertEquals(type2, type1.getParent().get());

		// check children
		Assert.assertEquals(2, type1.getChildren().size());
		Assert.assertTrue( type1.getChildren().contains(type3));
		Assert.assertTrue( type1.getChildren().contains(type4));
		Assert.assertEquals(1, type2.getChildren().size());
		Assert.assertTrue(type2.getChildren().contains(type1));
	}

	@Test
	public void invalidInheritanceTest() {
		ConstantType type1 = new ConstantType("T0");
		ConstantType type2 = new ConstantType("T1", type1);
		ConstantType type3 = new ConstantType("T2");
		Assert.assertThrows(RuntimeException.class, () -> type2.inherit(type3));
		Assert.assertThrows(RuntimeException.class, () -> type3.inherit(type3));
	}
}
