package ru.nsu.fit.hmtl.source.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.ApplicationType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;
import ru.nsu.fit.hmtl.core.typesystem.types.VaryingType;

import java.util.HashMap;
import java.util.Map;

public class ApplicationNodeTest {

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

	/// Basic type inference

	@Test
	public void testZeroArgsApplication() {
		Type func = TypeTable.getInstance().createVaryingType();
		storage.put("f", func);
		ConstantNode fn = new ConstantNode("f");
		ApplicationNode node = new ApplicationNode();
		node.addChild(fn);

		Type t = node.inferTypes(context);
		Assertions.assertInstanceOf(VaryingType.class, t);
		storage.remove("f");
	}

	@Test
	public void testSimpleApplication() {
		ApplicationType func = new ApplicationType(TypeTable.getInstance().getType("BT_Numeric"), TypeTable.getInstance().getType("BT_Numeric"));
		storage.put("f", func);
		ConstantNode fn = new ConstantNode("f");
		ConstantNode n1 = new ConstantNode("123");
		ApplicationNode node = new ApplicationNode();
		node.addChild(fn);
		node.addChild(n1);

		Type t = node.inferTypes(context);
		Assertions.assertEquals("BT_Numeric", t.getName());
		storage.remove("f");
	}

	@Test
	public void testPartialApplication() {
		ApplicationType at1 = new ApplicationType(TypeTable.getInstance().getType("BT_Numeric"), TypeTable.getInstance().getType("BT_Numeric"));
		ApplicationType at2 = new ApplicationType(TypeTable.getInstance().getType("BT_Numeric"), at1);
		storage.put("f", at2);
		ConstantNode fn = new ConstantNode("f");
		ConstantNode n1 = new ConstantNode("123");
		ConstantNode n2 = new ConstantNode("123");
		ApplicationNode an1 = new ApplicationNode();
		an1.addChild(fn);
		an1.addChild(n1);

		ApplicationNode an2 = new ApplicationNode();
		an2.addChild(an1);
		an2.addChild(n2);

		Type t = an2.inferTypes(context);
		Assertions.assertEquals("BT_Numeric", t.getName());
		Assertions.assertEquals("(BT_Numeric->BT_Numeric)", an1.getType().getName());
	}

	@Test
	public void testSimpleGenericApplication() {
		Type g1 = TypeTable.getInstance().createGenericType();
		ApplicationType at1 = new ApplicationType(g1, g1);
		storage.put("f", at1);
		ConstantNode fn = new ConstantNode("f");
		ConstantNode n1 = new ConstantNode("123");
		ApplicationNode an1 = new ApplicationNode();
		an1.addChild(fn);
		an1.addChild(n1);

		an1.inferTypes(context);
		an1.updateTypes(context);
		Assertions.assertEquals("BT_Numeric", an1.getType().getName());
	}

	@Test
	public void testGenericFunctionSubstitutionApplication() {
		Type g1 = TypeTable.getInstance().createGenericType();
		ApplicationType at1 = new ApplicationType(g1, g1);
		ApplicationType at2 = new ApplicationType(TypeTable.getInstance().getType("BT_Numeric"), TypeTable.getInstance().getType("BT_Numeric"));
		TypeTable.getInstance().registerFinalType(at2);
		storage.put("f", at1);
		storage.put("g", at2);

		ConstantNode fn = new ConstantNode("f");
		ConstantNode gn = new ConstantNode("g");
		ApplicationNode an1 = new ApplicationNode();
		an1.addChild(fn);
		an1.addChild(gn);

		an1.inferTypes(context);
		an1.updateTypes(context);
		Assertions.assertEquals("(BT_Numeric->BT_Numeric)", an1.getType().getName());
	}

	@Test
	public void testGenericToGenericApplication() {
		Type g1 = TypeTable.getInstance().createGenericType();
		ApplicationType at1 = new ApplicationType(g1, g1);
		storage.put("f", at1);

		ConstantNode fn = new ConstantNode("f");
		ConstantNode gn = new ConstantNode("f");
		ApplicationNode an1 = new ApplicationNode();
		an1.addChild(fn);
		an1.addChild(gn);

		an1.inferTypes(context);
		an1.updateTypes(context);
		an1.generify(context);

		Assertions.assertInstanceOf(ApplicationType.class, an1.getType());
		ApplicationType t1 = (ApplicationType) an1.getType();

		Type gen = t1.getLhs();

		Assertions.assertEquals(gen.getName(), t1.getRhs().getName());
		Assertions.assertEquals(gn.getType().getName(), t1.getName());
		Assertions.assertEquals(fn.getType().getName(), "(" + t1.getName() + "->" + t1.getName() + ")");
		storage.remove("f");
	}

	@Test
	public void testVarToVarApplication() {
		storage.put("f", TypeTable.getInstance().createVaryingType());
		storage.put("a", TypeTable.getInstance().createVaryingType());

		ConstantNode fn = new ConstantNode("f");
		ConstantNode a = new ConstantNode("a");
		ApplicationNode ap = new ApplicationNode();
		ap.addChild(fn);
		ap.addChild(a);

		ap.inferTypes(context);
		ap.updateTypes(context);
		ap.generify(context);

		Type t1 = ap.getType();
		Type t2 = a.getType();
		Assertions.assertEquals(fn.getType().getName(), "(" + t2.getName() + "->" + t1.getName() + ")");

		storage.remove("f");
		storage.remove("a");
	}

}
