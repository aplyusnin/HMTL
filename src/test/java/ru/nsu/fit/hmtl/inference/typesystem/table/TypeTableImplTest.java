package ru.nsu.fit.hmtl.inference.typesystem.table;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.nsu.fit.hmtl.inference.typesystem.TypeInferenceException;
import ru.nsu.fit.hmtl.inference.typesystem.types.BasicTypeBuilder;
import ru.nsu.fit.hmtl.inference.typesystem.types.DefaultTypeBuilder;
import ru.nsu.fit.hmtl.inference.typesystem.types.VariableTypeBuilder;

public class TypeTableImplTest {

	@Before
	public void setUp() {
		TypeTableImpl.getInstance().init();
	}

	@Test
	public void testTypeRegistration() {
		TypeTableImpl table = TypeTableImpl.getInstance();

		Assert.assertEquals(1, table.getNumberOfRegisteredTypes());
		table.createAndRegisterType(new DefaultTypeBuilder());
		Assert.assertEquals(2, table.getNumberOfRegisteredTypes());
		table.createAndRegisterType(new DefaultTypeBuilder());

		Assert.assertEquals(3, table.getNumberOfRegisteredTypes());
	}

	@Test
	public void testTypeAccessing() {
		TypeTableImpl table = TypeTableImpl.getInstance();

		int id1 = table.createAndRegisterType(new BasicTypeBuilder().setName(":Int"));
		Assert.assertEquals(":Int", table.getTypeByID(id1).getName());
		Assert.assertThrows(RuntimeException.class, () -> table.getTypeByID(id1 + 1));
	}

	@Test
	public void testRightUnification() {
		TypeTableImpl table = TypeTableImpl.getInstance();

		int id1 = table.createAndRegisterType(new BasicTypeBuilder().setName(":Int"));
		int id2 = table.createAndRegisterType(new VariableTypeBuilder());

		table.unifyTypes(id1, id2);

		Assert.assertEquals(table.getTypeByID(id1), table.getTypeByID(id2));
		Assert.assertEquals(3, table.getNumberOfRegisteredTypes());
	}

	@Test
	public void testLeftUnification() {
		TypeTableImpl table = TypeTableImpl.getInstance();

		int id1 = table.createAndRegisterType(new VariableTypeBuilder());
		int id2 = table.createAndRegisterType(new BasicTypeBuilder().setName(":Int"));

		table.unifyTypes(id1, id2);

		Assert.assertEquals(table.getTypeByID(id1), table.getTypeByID(id2));
		Assert.assertEquals(3, table.getNumberOfRegisteredTypes());
	}

	@Test
	public void testIncorrectUnification() {
		TypeTableImpl table = TypeTableImpl.getInstance();

		int id1 = table.createAndRegisterType(new BasicTypeBuilder().setName(":String"));
		int id2 = table.createAndRegisterType(new BasicTypeBuilder().setName(":Int"));
		Assert.assertThrows(TypeInferenceException.class, () -> table.unifyTypes(id1, id2));
	}
}
