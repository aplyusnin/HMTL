package ru.nsu.fit.hmtl.inference.typesystem.unification;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.hmtl.inference.typesystem.types.*;

public class ConstantTypeUnificationStrategyTest {

	@Test
	public void testConstantSubstitution() {
		Type t1 = new BasicTypeBuilder().setId(1).build();
		Type t2 = new BasicTypeBuilder().setId(2).build();
		Assert.assertTrue(
				ConstantUnificationStrategy
						.getInstance()
						.isSubstitutableWith(t1, t1));
		Assert.assertFalse(
				ConstantUnificationStrategy
						.getInstance()
						.isSubstitutableWith(t1, t2));
	}

	@Test
	public void testOtherSubstitution() {
		Type t1 = new DefaultTypeBuilder().setId(3).build();
		Type t2 = new VariableTypeBuilder().setId(2).build();
		Type t3 = new BasicTypeBuilder().setId(1).build();
		Assert.assertFalse(
				ConstantUnificationStrategy
						.getInstance()
						.isSubstitutableWith(t3, t1));
		Assert.assertFalse(
				ConstantUnificationStrategy
						.getInstance()
						.isSubstitutableWith(t3, t2));
		Assert.assertFalse(
				ConstantUnificationStrategy
						.getInstance()
						.isSubstitutableWith(t3, EmptyType.getInstance()));
	}

	@Test
	public void testConstantUnification() {
		Type t1 = new BasicTypeBuilder().setId(1).build();
		Type t2 = new BasicTypeBuilder().setId(2).build();
		Assert.assertTrue(
				ConstantUnificationStrategy
						.getInstance()
						.unifyWith(t1, t1).isPresent());
		Assert.assertFalse(
				ConstantUnificationStrategy
						.getInstance()
						.unifyWith(t1, t2).isPresent());
	}

	@Test
	public void testFailUnification() {
		Type t1 = new DefaultTypeBuilder().setId(1).build();
		Type t2 = new VariableTypeBuilder().setId(2).build();
		Type t3 = new BasicTypeBuilder().setId(3).build();
		Assert.assertFalse(
				ConstantUnificationStrategy
						.getInstance()
						.unifyWith(t3, t1).isPresent());
		Assert.assertFalse(
				ConstantUnificationStrategy
						.getInstance()
						.unifyWith(t3, t2).isPresent());
		Assert.assertFalse(
				ConstantUnificationStrategy
						.getInstance()
						.unifyWith(t3, EmptyType.getInstance()).isPresent());
	}
}
