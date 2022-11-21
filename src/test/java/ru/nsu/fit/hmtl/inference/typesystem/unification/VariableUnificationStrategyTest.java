package ru.nsu.fit.hmtl.inference.typesystem.unification;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.hmtl.inference.typesystem.types.*;

public class VariableUnificationStrategyTest {
	@Test
	public void testVariableSubstitution() {
		Type t1 = new VariableTypeBuilder().setId(1).build();
		Type t2 = new VariableTypeBuilder().setId(2).build();
		Assert.assertTrue(
				VariableUnificationStrategy
						.getInstance()
						.isSubstitutableWith(t1, t1));
		Assert.assertTrue(
				VariableUnificationStrategy
						.getInstance()
						.isSubstitutableWith(t1, t2));
	}

	@Test
	public void testOtherSubstitution() {
		Type t1 = new DefaultTypeBuilder().setId(3).build();
		Type t2 = new VariableTypeBuilder().setId(2).build();
		Type t3 = new BasicTypeBuilder().setId(1).build();
		Assert.assertTrue(
				VariableUnificationStrategy
						.getInstance()
						.isSubstitutableWith(t2, t1));
		Assert.assertTrue(
				VariableUnificationStrategy
						.getInstance()
						.isSubstitutableWith(t2, t3));
		Assert.assertFalse(
				VariableUnificationStrategy
						.getInstance()
						.isSubstitutableWith(t3, EmptyType.getInstance()));
	}

	@Test
	public void testConstantUnification() {
		Type t1 = new BasicTypeBuilder().setId(1).build();
		Type t2 = new BasicTypeBuilder().setId(2).build();
		Assert.assertTrue(
				VariableUnificationStrategy
						.getInstance()
						.unifyWith(t1, t1).isPresent());
		Assert.assertTrue(
				VariableUnificationStrategy
						.getInstance()
						.unifyWith(t1, t2).isPresent());
	}

	@Test
	public void testFailUnification() {
		Type t1 = new DefaultTypeBuilder().setId(1).build();
		Type t2 = new VariableTypeBuilder().setId(2).build();
		Type t3 = new BasicTypeBuilder().setId(3).build();
		Assert.assertTrue(
				VariableUnificationStrategy
						.getInstance()
						.unifyWith(t2, t1).isPresent());
		Assert.assertTrue(
				VariableUnificationStrategy
						.getInstance()
						.unifyWith(t2, t3).isPresent());
		Assert.assertFalse(
				VariableUnificationStrategy
						.getInstance()
						.unifyWith(t2, EmptyType.getInstance()).isPresent());
	}
}
