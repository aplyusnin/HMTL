package ru.nsu.fit.hmtl.source.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.HashMap;
import java.util.Map;

public class ConstantNodeTest {

	private static TypeContext context;
	private static Map<String, Type> storage;


	@BeforeAll
	public static void setUp() {
		storage = new HashMap<>();
		context = new TypeContext() {

			@Override
			public Type lookup(String name) {
				return storage.get(name);
			}

			@Override
			public TypeContext createSubContext() {
				return null;
			}

			@Override
			public void setType(String name, Type type) {
			}
		};
		storage.put("123", TypeTable.getInstance().getType("BT_Numeric"));
		storage.put("True", TypeTable.getInstance().getType("BT_Bool"));
		storage.put("0xff", TypeTable.getInstance().getType("BT_Byte"));
	}

	@Test
	public void testGetConstantValue() {
		ConstantNode n1 = new ConstantNode("123");
		ConstantNode n2 = new ConstantNode("True");
		ConstantNode n3 = new ConstantNode("0xff");
		ConstantNode n4 = new ConstantNode("fail");

		Assertions.assertEquals("BT_Numeric", n1.inferTypesInternal(context).getName());
		Assertions.assertEquals("BT_Bool", n2.inferTypesInternal(context).getName());
		Assertions.assertEquals("BT_Byte", n3.inferTypesInternal(context).getName());
		Assertions.assertThrows(RuntimeException.class, () -> n4.inferTypesInternal(context).getName());
	}
}
