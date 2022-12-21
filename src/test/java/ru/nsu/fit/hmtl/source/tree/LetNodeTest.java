package ru.nsu.fit.hmtl.source.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.core.typesystem.context.TypeContextImpl;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.ApplicationType;
import ru.nsu.fit.hmtl.core.typesystem.types.GenericType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.HashMap;
import java.util.Map;

public class LetNodeTest {

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
				return new TypeContextImpl(this);
			}

			@Override
			public void setType(String name, Type type) {
				storage.put(name, type);
			}
		};
		storage.put("123", TypeTable.getInstance().getType("BT_Numeric"));
		storage.put("True", TypeTable.getInstance().getType("BT_Bool"));
		storage.put("0xff", TypeTable.getInstance().getType("BT_Byte"));
		storage.put("a", TypeTable.getInstance().getType("BT_Bool"));
	}

	@Test
	public void testBasicTypedVarDefinition() {
		VariableNode varNode = new VariableNode("v", TypeTable.getInstance().getType("BT_Numeric"));
		ConstantNode cNode = new ConstantNode("123");
		ConstantNode bNode = new ConstantNode("v");

		LetNode let = new LetNode();
		let.addChild(varNode);
		let.addChild(cNode);
		let.addChild(bNode);

		let.inferTypes(context);

		Assertions.assertEquals("BT_Numeric", let.getType().getName());
	}

	@Test
	public void testVarRedefinition() {
		VariableNode varNode = new VariableNode("a", TypeTable.getInstance().getType("BT_Numeric"));
		ConstantNode cNode = new ConstantNode("123");
		ConstantNode bNode = new ConstantNode("a");

		LetNode let = new LetNode();
		let.addChild(varNode);
		let.addChild(cNode);
		let.addChild(bNode);

		let.inferTypes(context);

		Assertions.assertEquals("BT_Numeric", let.getType().getName());
	}

	@Test
	public void testOtherVarRedefinition() {
		VariableNode varNode = new VariableNode("b", TypeTable.getInstance().getType("BT_Numeric"));
		ConstantNode cNode = new ConstantNode("123");
		ConstantNode bNode = new ConstantNode("a");

		LetNode let = new LetNode();
		let.addChild(varNode);
		let.addChild(cNode);
		let.addChild(bNode);

		let.inferTypes(context);

		Assertions.assertEquals("BT_Bool", let.getType().getName());
	}

	@Test
	public void testNamingAbstraction() {
		Type t = new ApplicationType(TypeTable.getInstance().createVaryingType(), TypeTable.getInstance().createVaryingType());
		TypeTable.getInstance().registerFinalType(t);
		VariableNode varNode = new VariableNode("f", TypeTable.getInstance().createVaryingType());
		VariableNode cNode = new VariableNode("g", t);
		ApplicationNode an = new ApplicationNode();
		ConstantNode fNode = new ConstantNode("f");
		ConstantNode bNode = new ConstantNode("123");

		an.addChild(fNode);
		an.addChild(bNode);

		LetNode let = new LetNode();
		let.addChild(varNode);
		let.addChild(cNode);
		let.addChild(an);

		let.inferTypes(context);
		let.updateTypes(context);
		let.generify(context);

		Assertions.assertInstanceOf(GenericType.class, let.getType());
		Assertions.assertEquals("(BT_Numeric->", varNode.getType().getName().substring(0, 13));
	}

	@Test
	public void testOverlapping() {
		VariableNode var1 = new VariableNode("v", TypeTable.getInstance().getType("BT_Bool"));
		VariableNode var2 = new VariableNode("v", TypeTable.getInstance().createVaryingType());
		ConstantNode c1 = new ConstantNode("v");
		ConstantNode c2 = new ConstantNode("v");
		ConstantNode c3 = new ConstantNode("v");
		LetNode let1 = new LetNode();
		LetNode let2 = new LetNode();
		let2.addChild(var2);
		let2.addChild(c2);
		let2.addChild(c3);
		let1.addChild(var1);
		let1.addChild(c1);
		let1.addChild(let2);

		let1.inferTypes(context);
		let1.updateTypes(context);
		let1.generify(context);
		Assertions.assertInstanceOf(GenericType.class, let1.getType());
	}
}
