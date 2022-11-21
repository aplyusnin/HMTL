package ru.nsu.fit.hmtl.inference.typesystem.unification;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.nsu.fit.hmtl.inference.typesystem.table.TypeTableImpl;
import ru.nsu.fit.hmtl.inference.typesystem.types.*;

import java.util.Optional;

public class ComplexUnificationStrategyTest {

	@Before
	public void setup() {
		TypeTableImpl.getInstance().init();
	}

	@Test
	public void testArityMismatchSubstitution() {
		Type vt = new VariableTypeBuilder().setId(1).build();
		Type bt = new BasicTypeBuilder().setId(2).build();
		Type f1 = new DefaultTypeBuilder().setLeftHandSide(vt).setRightHandSide(vt).setId(3).build();
		Type f2 = new DefaultTypeBuilder().setLeftHandSide(vt).setRightHandSide(bt).setId(3).build();

		Assert.assertFalse(ComplexUnificationStrategy.getInstance().isSubstitutableWith(f1, vt));
		Assert.assertFalse(ComplexUnificationStrategy.getInstance().isSubstitutableWith(f1, bt));
		Assert.assertTrue(ComplexUnificationStrategy.getInstance().isSubstitutableWith(f1, f2));

	}

	@Test
	public void testSimpleApplicationSubstitution() {
		Type vT1 = new VariableTypeBuilder().setId(1).build();
		Type vT2 = new VariableTypeBuilder().setId(2).build();
		Type vT3 = new VariableTypeBuilder().setId(3).build();
		Type vT4 = new VariableTypeBuilder().setId(4).build();

		Type bT1 = new BasicTypeBuilder().setId(5).setName(":Int").build();
		Type bT2 = new BasicTypeBuilder().setId(6).setName(":Str").build();

		Type f1 = new DefaultTypeBuilder().setLeftHandSide(vT1).setRightHandSide(vT2).setId(7).build();
		Type f2 = new DefaultTypeBuilder().setLeftHandSide(vT3).setRightHandSide(bT2).setId(8).build();
		Type f3 = new DefaultTypeBuilder().setLeftHandSide(bT1).setRightHandSide(vT4).setId(9).build();

		Assert.assertTrue(ComplexUnificationStrategy.getInstance().isSubstitutableWith(f1, f2));
		Assert.assertTrue(ComplexUnificationStrategy.getInstance().isSubstitutableWith(f1, f3));
		Assert.assertFalse(ComplexUnificationStrategy.getInstance().isSubstitutableWith(f2, f3));
	}

	@Test
	public void testArityMismatchUnification() {
		Type vt = new VariableTypeBuilder().setId(1).build();
		Type bt = new BasicTypeBuilder().setId(2).build();
		Type f1 = new DefaultTypeBuilder().setLeftHandSide(vt).setRightHandSide(vt).setId(3).build();
		Type f2 = new DefaultTypeBuilder().setLeftHandSide(vt).setRightHandSide(bt).setId(3).build();

		Assert.assertFalse(ComplexUnificationStrategy.getInstance().unifyWith(f1, vt).isPresent());
		Assert.assertFalse(ComplexUnificationStrategy.getInstance().unifyWith(f1, bt).isPresent());
		Assert.assertTrue(ComplexUnificationStrategy.getInstance().unifyWith(f1, f2).isPresent());
	}

	@Test
	public void testFailedUnification() {
		int vT1 = TypeTableImpl.getInstance().createAndRegisterType(new VariableTypeBuilder());

		int bT1 = TypeTableImpl.getInstance().createAndRegisterType(new BasicTypeBuilder().setName(":Int"));
		int bT2 = TypeTableImpl.getInstance().createAndRegisterType(new BasicTypeBuilder().setName(":Str"));

		int f1 = TypeTableImpl.getInstance().createAndRegisterType(
				new DefaultTypeBuilder()
						.setLeftHandSide(TypeTableImpl.getInstance().getTypeByID(vT1))
						.setRightHandSide(TypeTableImpl.getInstance().getTypeByID(bT1)));
		int f2 = TypeTableImpl.getInstance().createAndRegisterType(
				new DefaultTypeBuilder()
						.setLeftHandSide(TypeTableImpl.getInstance().getTypeByID(bT1))
						.setRightHandSide(TypeTableImpl.getInstance().getTypeByID(bT2)));

		Assert.assertFalse(ComplexUnificationStrategy
				                   .getInstance()
				                   .unifyWith(
						                   TypeTableImpl.getInstance().getTypeByID(f1),
						                   TypeTableImpl.getInstance().getTypeByID(f2))
				                   .isPresent());
	}
	@Test
	public void testUnificationSucceed() {
		int vT1 = TypeTableImpl.getInstance().createAndRegisterType(new VariableTypeBuilder());

		int bT1 = TypeTableImpl.getInstance().createAndRegisterType(new BasicTypeBuilder().setName(":Int"));
		int bT2 = TypeTableImpl.getInstance().createAndRegisterType(new BasicTypeBuilder().setName(":Str"));

		int f1 = TypeTableImpl.getInstance().createAndRegisterType(
				new DefaultTypeBuilder()
						.setLeftHandSide(TypeTableImpl.getInstance().getTypeByID(vT1))
						.setRightHandSide(TypeTableImpl.getInstance().getTypeByID(bT1)));
		int f2 = TypeTableImpl.getInstance().createAndRegisterType(
				new DefaultTypeBuilder()
						.setLeftHandSide(TypeTableImpl.getInstance().getTypeByID(bT2))
						.setRightHandSide(TypeTableImpl.getInstance().getTypeByID(bT1)));

		int f3 = TypeTableImpl.getInstance().createAndRegisterType(
				new DefaultTypeBuilder()
						.setLeftHandSide(TypeTableImpl.getInstance().getTypeByID(f2))
						.setRightHandSide(TypeTableImpl.getInstance().getTypeByID(bT1)));

		Optional<Type> value = ComplexUnificationStrategy
				.getInstance()
				.unifyWith(
						TypeTableImpl.getInstance().getTypeByID(f1),
						TypeTableImpl.getInstance().getTypeByID(f3));

		Assert.assertTrue(value.isPresent());
		Assert.assertEquals("((:Str->:Int)->:Int)", value.get().getName());
	}
}
