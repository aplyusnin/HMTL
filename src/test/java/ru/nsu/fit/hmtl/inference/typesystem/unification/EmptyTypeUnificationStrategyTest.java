package ru.nsu.fit.hmtl.inference.typesystem.unification;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.hmtl.inference.typesystem.types.*;

public class EmptyTypeUnificationStrategyTest {

	@Test
	public void testEmptySubstitution() {
		Assert.assertTrue(
				EmptyTypeUnificationStrategy
						.getInstance()
						.isSubstitutableWith(EmptyType.getInstance(), EmptyType.getInstance()));
	}

	@Test
	public void testFailSubstitution() {
		Type t1 = new DefaultTypeBuilder().build();
		Type t2 = new VariableTypeBuilder().build();
		Type t3 = new BasicTypeBuilder().build();
		Assert.assertFalse(
				EmptyTypeUnificationStrategy
						.getInstance()
						.isSubstitutableWith(EmptyType.getInstance(),t1));
		Assert.assertFalse(
				EmptyTypeUnificationStrategy
						.getInstance()
						.isSubstitutableWith(EmptyType.getInstance(),t2));
		Assert.assertFalse(
				EmptyTypeUnificationStrategy
						.getInstance()
						.isSubstitutableWith(EmptyType.getInstance(),t3));
	}

	@Test
	public void testEmptyUnification() {
		Assert.assertTrue(
				EmptyTypeUnificationStrategy
						.getInstance()
						.unifyWith(EmptyType.getInstance(), EmptyType.getInstance()).isPresent());
	}

	@Test
	public void testFailUnification() {
		Type t1 = new DefaultTypeBuilder().build();
		Type t2 = new VariableTypeBuilder().build();
		Type t3 = new BasicTypeBuilder().build();
		Assert.assertFalse(
				EmptyTypeUnificationStrategy
						.getInstance()
						.unifyWith(EmptyType.getInstance(),t1).isPresent());
		Assert.assertFalse(
				EmptyTypeUnificationStrategy
						.getInstance()
						.unifyWith(EmptyType.getInstance(),t2).isPresent());
		Assert.assertFalse(
				EmptyTypeUnificationStrategy
						.getInstance()
						.unifyWith(EmptyType.getInstance(),t3).isPresent());
	}
}
