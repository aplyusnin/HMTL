package ru.nsu.fit.hmtl.source.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.hmtl.core.typesystem.TypeUtils;
import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.ApplicationType;
import ru.nsu.fit.hmtl.core.typesystem.types.GenericType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.HashMap;
import java.util.Map;

public class VariableNodeTest {
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
		Type num = TypeTable.getInstance().getType("BT_Numeric");
	}

	@Test
	public void testBasicType() {
		VariableNode node = new VariableNode("a", TypeTable.getInstance().getType("BT_Numeric"));
		node.inferTypes(context);
		Assertions.assertEquals("BT_Numeric", node.getType().getName());
	}

	@Test
	public void testVariableType() {
		Type t = TypeTable.getInstance().createVaryingType();
		VariableNode node = new VariableNode("a", t);

		node.inferTypes(context);
		Assertions.assertEquals(t.getName(), node.getType().getName());

		TypeUtils.unify(t, TypeTable.getInstance().getType("BT_Numeric"));
		node.updateTypes(context);
		Assertions.assertEquals("BT_Numeric", node.getType().getName());
	}

	@Test
	public void testGenericType() {
		Type t = TypeTable.getInstance().createVaryingType();
		VariableNode node = new VariableNode("a", t);

		node.inferTypes(context);
		Assertions.assertEquals(t.getName(), node.getType().getName());

		node.updateTypes(context);
		node.generify(context);
		Assertions.assertInstanceOf(GenericType.class, node.getType());
	}
}
