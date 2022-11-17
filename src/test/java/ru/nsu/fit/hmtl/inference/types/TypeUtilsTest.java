package ru.nsu.fit.hmtl.inference.types;

import org.junit.Assert;
import org.junit.Test;

public class TypeUtilsTest {
	@Test
	public void testAbstraction() {
		Type lhs = new BasicTypeBuilder().setName("Int").build();
		Type rhs = new BasicTypeBuilder().setName("String").build();

		Type f = TypeUtils.abstraction(lhs, rhs);
		Assert.assertEquals("(Int->String)", f.getName());
	}

	@Test
	public void testApplicationBasic() {
		Type lhs = new BasicTypeBuilder().setName("Int").build();
		Type rhs = new BasicTypeBuilder().setName("String").build();

		Type f = TypeUtils.abstraction(lhs, rhs);
		Assert.assertThrows("Type mismatch during an application", TypeInferenceException.class, () -> TypeUtils.application(f, rhs));
		try {
			Type t = TypeUtils.application(f, lhs);
			Assert.assertEquals(rhs.getName(), t.getName());
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testSubstitutable() {
		// Variable
		Type variable = new DefaultTypeBuilder().build();
		// Basic
		Type constant = new BasicTypeBuilder().setName("Int").build();

		// T -> S
		Type f1 =
				new DefaultTypeBuilder()
						.setLeftHandSide(new DefaultTypeBuilder().build())
						.setRightHandSide(new DefaultTypeBuilder().build())
						.build();

		// T -> Int
		Type f2 =
				new DefaultTypeBuilder()
						.setLeftHandSide(new DefaultTypeBuilder().build())
						.setRightHandSide(constant)
						.build();
		// Int -> S
		Type f3 =
				new DefaultTypeBuilder()
						.setLeftHandSide(constant)
						.setRightHandSide(new DefaultTypeBuilder().build())
						.build();

		// Int -> Int
		Type f4 =
				new DefaultTypeBuilder()
						.setLeftHandSide(constant)
						.setRightHandSide(constant)
						.build();

		// Types can be substituted by itself
		Assert.assertTrue(TypeUtils.substitutable(constant, constant));
		Assert.assertTrue(TypeUtils.substitutable(variable, variable));
		Assert.assertTrue(TypeUtils.substitutable(f1, f1));
		Assert.assertTrue(TypeUtils.substitutable(f2, f2));
		Assert.assertTrue(TypeUtils.substitutable(f3, f3));
		Assert.assertTrue(TypeUtils.substitutable(f4, f4));

		// Basic type cannot be substituted
		Assert.assertFalse(TypeUtils.substitutable(constant, variable));
		Assert.assertFalse(TypeUtils.substitutable(constant, f1));
		Assert.assertFalse(TypeUtils.substitutable(constant, f2));
		Assert.assertFalse(TypeUtils.substitutable(constant, f3));
		Assert.assertFalse(TypeUtils.substitutable(constant, f4));

		// Variable can be substituted by everything
		Assert.assertTrue(TypeUtils.substitutable(variable, constant));
		Assert.assertTrue(TypeUtils.substitutable(variable, f1));
		Assert.assertTrue(TypeUtils.substitutable(variable, f2));
		Assert.assertTrue(TypeUtils.substitutable(variable, f3));
		Assert.assertTrue(TypeUtils.substitutable(variable, f4));

		// Function cannot be substituted by constant or variable
		Assert.assertFalse(TypeUtils.substitutable(f1, variable));
		Assert.assertFalse(TypeUtils.substitutable(f1, constant));

		// Basic typed function cannot be substituted by general ones
		Assert.assertFalse(TypeUtils.substitutable(f4, f1));
		Assert.assertFalse(TypeUtils.substitutable(f4, f2));
		Assert.assertFalse(TypeUtils.substitutable(f4, f3));

		// Varying type function can be substituted by every other
		Assert.assertTrue(TypeUtils.substitutable(f1, f2));
		Assert.assertTrue(TypeUtils.substitutable(f1, f3));
		Assert.assertTrue(TypeUtils.substitutable(f1, f4));

		// f2 cannot substitute f3 and wise-versa due to substitution of basic type with a common one
		Assert.assertFalse(TypeUtils.substitutable(f2, f3));
		Assert.assertFalse(TypeUtils.substitutable(f3, f2));

	}
}
