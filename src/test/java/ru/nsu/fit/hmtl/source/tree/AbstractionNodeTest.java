package ru.nsu.fit.hmtl.source.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.core.typesystem.context.TypeContextImpl;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.HashMap;
import java.util.Map;

public class AbstractionNodeTest {

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
	}

	@Test
	public void testSingleBasicAbstraction() {
		VariableNode name = new VariableNode("f", TypeTable.getInstance().createVaryingType());
		VariableNode arg=  new VariableNode("a", TypeTable.getInstance().getType("BT_Numeric"));
		TreeNode body = new ConstantNode("123");
		AbstractionNode ab = new AbstractionNode();
		ab.addChild(name);
		ab.addChild(arg);
		ab.addChild(body);

		ab.inferTypes(context);
		ab.updateTypes(context);
		ab.generify(context);
		Assertions.assertEquals("(BT_Numeric->BT_Numeric)", context.lookup("f").getName());
		storage.remove("f");
	}

	@Test
	public void testDoubleBasicAbstraction() {
		VariableNode name = new VariableNode("f", TypeTable.getInstance().createVaryingType());
		VariableNode arg1 =  new VariableNode("a", TypeTable.getInstance().getType("BT_Numeric"));
		VariableNode arg2 =  new VariableNode("b", TypeTable.getInstance().getType("BT_Numeric"));
		TreeNode body = new ConstantNode("123");
		AbstractionNode ab = new AbstractionNode();
		ab.addChild(name);
		ab.addChild(arg1);
		ab.addChild(arg2);
		ab.addChild(body);

		ab.inferTypes(context);
		ab.updateTypes(context);
		ab.generify(context);
		Assertions.assertEquals("(BT_Numeric->(BT_Numeric->BT_Numeric))", context.lookup("f").getName());
		storage.remove("f");
	}

	@Test
	public void testDoubleBasicWithGenericAbstraction() {
		VariableNode name = new VariableNode("f", TypeTable.getInstance().createVaryingType());
		VariableNode arg1 =  new VariableNode("a", TypeTable.getInstance().getType("BT_Numeric"));
		VariableNode arg2 =  new VariableNode("b", TypeTable.getInstance().createVaryingType());
		TreeNode body = new ConstantNode("123");
		AbstractionNode ab = new AbstractionNode();
		ab.addChild(name);
		ab.addChild(arg1);
		ab.addChild(arg2);
		ab.addChild(body);

		ab.inferTypes(context);
		ab.updateTypes(context);
		ab.generify(context);
		Assertions.assertEquals("(BT_Numeric->(GT_0->BT_Numeric))", context.lookup("f").getName());
		storage.remove("f");
	}

	@Test
	public void testZeroArgsAbstraction() {
		VariableNode name = new VariableNode("f", TypeTable.getInstance().createVaryingType());
		TreeNode body = new ConstantNode("123");
		AbstractionNode ab = new AbstractionNode();
		ab.addChild(name);
		ab.addChild(body);

		ab.inferTypes(context);
		ab.updateTypes(context);
		ab.generify(context);
		Assertions.assertEquals("BT_Numeric", context.lookup("f").getName());
		storage.remove("f");
	}
}
