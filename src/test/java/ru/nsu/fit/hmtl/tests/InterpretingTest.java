package ru.nsu.fit.hmtl.tests;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.StlExecutionContext;
import ru.nsu.fit.hmtl.core.lang.BasicObject;
import ru.nsu.fit.hmtl.core.lang.BasicUtils;
import ru.nsu.fit.hmtl.core.typesystem.context.StlTypeContext;
import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.ApplicationType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;
import ru.nsu.fit.hmtl.core.typesystem.types.VaryingType;
import ru.nsu.fit.hmtl.source.tree.*;

import static org.junit.jupiter.api.Assertions.*;

public class InterpretingTest {

	@Test
	public void testSimpleAddInterpreting() {

		TypeContext tctx = StlTypeContext.getInstance().createSubContext();
		ExecutionContext ectx = StlExecutionContext.getInstance().createSubContext();

		ConstantNode c1 = new ConstantNode("print");
		ConstantNode c2 = new ConstantNode("+");
		ConstantNode c3 = new ConstantNode("7");
		ConstantNode c4 = new ConstantNode("9");

		ApplicationNode sum = new ApplicationNode();
		sum.addChild(c2);
		sum.addChild(c3);
		sum.addChild(c4);

		ApplicationNode print = new ApplicationNode();
		print.addChild(c1);
		print.addChild(sum);

		print.inferTypes(tctx);
		print.updateTypes(tctx);

		var expr = print.generateExpression();
		var res = expr.eval(ectx);

		assertInstanceOf(BasicObject.class, res);

		BasicObject o = (BasicObject) res;

		assertEquals("BT_Numeric", o.getType().getName());
		assertEquals(16, (Integer)o.getValue());
	}

	@Test
	public void testGenericInterpreting() {

		TypeContext tctx = StlTypeContext.getInstance().createSubContext();
		ExecutionContext ectx = StlExecutionContext.getInstance().createSubContext();

		ConstantNode c0 = new ConstantNode("print");
		ConstantNode c1 = new ConstantNode("if");
		ConstantNode c2 = new ConstantNode("trUe");
		ConstantNode c3 = new ConstantNode("-");
		ConstantNode c4 = new ConstantNode("+");
		ConstantNode c5 = new ConstantNode("9");
		ConstantNode c6 = new ConstantNode("7");

		ApplicationNode ifn = new ApplicationNode();
		ifn.addChild(c1);
		ifn.addChild(c2);
		ifn.addChild(c3);
		ifn.addChild(c4);

		ApplicationNode app = new ApplicationNode();
		app.addChild(ifn);
		app.addChild(c5);
		app.addChild(c6);

		ApplicationNode print = new ApplicationNode();
		print.addChild(c0);
		print.addChild(app);


		print.inferTypes(tctx);
		print.updateTypes(tctx);

		var expr = print.generateExpression();
		var res = expr.eval(ectx);

		assertInstanceOf(BasicObject.class, res);

		BasicObject o = (BasicObject) res;

		assertEquals("BT_Numeric", o.getType().getName());
		assertEquals(2, (Integer)o.getValue());
	}

	@Test
	public void testPartiallyAppliedInterpreting() {

		TypeContext tctx = StlTypeContext.getInstance().createSubContext();
		ExecutionContext ectx = StlExecutionContext.getInstance().createSubContext();

		ConstantNode c0 = new ConstantNode("print");
		ConstantNode c1 = new ConstantNode("if");
		ConstantNode c2 = new ConstantNode("trUe");
		ConstantNode c3 = new ConstantNode("-");
		ConstantNode c4 = new ConstantNode("9");
		ConstantNode c5 = new ConstantNode("+");
		ConstantNode c6 = new ConstantNode("9");
		ConstantNode c7 = new ConstantNode("7");

		ApplicationNode ifn = new ApplicationNode();
		ifn.addChild(c1);
		ifn.addChild(c2);
		ApplicationNode tr = new ApplicationNode();
		tr.addChild(c3);
		tr.addChild(c4);
		ApplicationNode fl = new ApplicationNode();
		fl.addChild(c5);
		fl.addChild(c6);
		ifn.addChild(tr);
		ifn.addChild(fl);

		ApplicationNode app = new ApplicationNode();
		app.addChild(ifn);
		app.addChild(c7);

		ApplicationNode print = new ApplicationNode();
		print.addChild(c0);
		print.addChild(app);

		print.inferTypes(tctx);
		print.updateTypes(tctx);

		var expr = print.generateExpression();
		var res = expr.eval(ectx);

		assertInstanceOf(BasicObject.class, res);

		BasicObject o = (BasicObject) res;

		assertEquals("BT_Numeric", o.getType().getName());
		assertEquals(2, (Integer)o.getValue());
	}

	@Test
	public void testLetValue() {
		TypeContext tctx = StlTypeContext.getInstance().createSubContext();
		ExecutionContext ectx = StlExecutionContext.getInstance().createSubContext();

		ConstantNode cp = new ConstantNode("print");
		LetNode ln = new LetNode();
		VariableNode nm = new VariableNode("a", TypeTable.getInstance().createVaryingType());
		ConstantNode vl = new ConstantNode("13");

		ConstantNode c0 = new ConstantNode("+");
		ConstantNode c1 = new ConstantNode("a");
		ConstantNode c2 = new ConstantNode("13");

		ApplicationNode appInner = new ApplicationNode();
		appInner.addChild(c0);
		appInner.addChild(c1);
		appInner.addChild(c2);

		ln.addChild(nm);
		ln.addChild(vl);
		ln.addChild(appInner);

		ApplicationNode print = new ApplicationNode();
		print.addChild(cp);
		print.addChild(ln);

		print.inferTypes(tctx);
		print.updateTypes(tctx);

		var expr = print.generateExpression();
		var res = expr.eval(ectx);

		assertInstanceOf(BasicObject.class, res);

		BasicObject o = (BasicObject) res;

		assertEquals("BT_Numeric", o.getType().getName());
		assertEquals(26, (Integer)o.getValue());
	}

	@Test
	public void testAbstraction() {
		TypeContext tctx = StlTypeContext.getInstance().createSubContext();
		ExecutionContext ectx = StlExecutionContext.getInstance().createSubContext();

		AbstractionNode abn = new AbstractionNode();
		VariableNode fn = new VariableNode("sqr", BasicUtils.getInt());
		VariableNode arg = new VariableNode("a", BasicUtils.getInt());

		ConstantNode c0 = new ConstantNode("*");
		ConstantNode c1 = new ConstantNode("a");
		ConstantNode c2 = new ConstantNode("a");

		ApplicationNode bd = new ApplicationNode();
		bd.addChild(c0);
		bd.addChild(c1);
		bd.addChild(c2);

		abn.addChild(fn);
		abn.addChild(arg);
		abn.addChild(bd);

		ConstantNode cp = new ConstantNode("print");
		ConstantNode c3 = new ConstantNode("sqr");
		ConstantNode c4 = new ConstantNode("17");

		ApplicationNode an1 = new ApplicationNode();
		an1.addChild(c3);
		an1.addChild(c4);

		ApplicationNode print = new ApplicationNode();
		print.addChild(cp);
		print.addChild(an1);

		abn.inferTypes(tctx);
		abn.updateTypes(tctx);
		abn.generify(tctx);
		var ex1 = abn.generateExpression();
		ex1.eval(ectx);

		print.inferTypes(tctx);
		print.updateTypes(tctx);
		print.generify(tctx);

		var expr = print.generateExpression();
		var res = expr.eval(ectx);

		assertInstanceOf(BasicObject.class, res);

		BasicObject o = (BasicObject) res;

		assertEquals("BT_Numeric", o.getType().getName());
		assertEquals(289, (Integer)o.getValue());
	}

	@Test
	public void testTwoArgAbstraction() {
		TypeContext tctx = StlTypeContext.getInstance().createSubContext();
		ExecutionContext ectx = StlExecutionContext.getInstance().createSubContext();

		// (defn fun [a b] (/ (* a a) b))

		AbstractionNode abn = new AbstractionNode();
		VariableNode fn = new VariableNode("fun", TypeTable.getInstance().createVaryingType());
		VariableNode arg1 = new VariableNode("ARG_a", TypeTable.getInstance().createVaryingType());
		VariableNode arg2 = new VariableNode("ARG_b", TypeTable.getInstance().createVaryingType());

		ConstantNode c0 = new ConstantNode("/");
		ConstantNode c1 = new ConstantNode("*");
		ConstantNode c2 = new ConstantNode("ARG_a");
		ConstantNode c3 = new ConstantNode("ARG_a");
		ConstantNode c4 = new ConstantNode("ARG_b");

		ApplicationNode bd1 = new ApplicationNode();
		bd1.addChild(c1);
		bd1.addChild(c2);
		bd1.addChild(c3);

		ApplicationNode bd2 = new ApplicationNode();
		bd2.addChild(c0);
		bd2.addChild(bd1);
		bd2.addChild(c4);

		abn.addChild(fn);
		abn.addChild(arg1);
		abn.addChild(arg2);
		abn.addChild(bd2);

		abn.inferTypes(tctx);
		abn.updateTypes(tctx);
		abn.generify(tctx);
		var ex1 = abn.generateExpression();
		ex1.eval(ectx);


		// (print (let [a (fun 17)] (a (a 17))))

		ConstantNode cp = new ConstantNode("print");
		ConstantNode c_0 = new ConstantNode("fun");
		ConstantNode c_1 = new ConstantNode("17");
		ConstantNode c_2 = new ConstantNode("LT_a");
		ConstantNode c_3 = new ConstantNode("LT_a");
		ConstantNode c_4 = new ConstantNode("17");

		VariableNode v_0 = new VariableNode("LT_a");

		ApplicationNode app1 = new ApplicationNode();
		app1.addChild(c_0);
		app1.addChild(c_1);

		ApplicationNode app3 = new ApplicationNode();
		app3.addChild(c_3);
		app3.addChild(c_4);

		ApplicationNode app2 = new ApplicationNode();
		app2.addChild(c_2);
		app2.addChild(app3);

		LetNode ln = new LetNode();
		ln.addChild(v_0);
		ln.addChild(app1);
		ln.addChild(app2);

		ApplicationNode print = new ApplicationNode();
		print.addChild(cp);
		print.addChild(ln);

		print.inferTypes(tctx);
		print.updateTypes(tctx);
		print.generify(tctx);

		var expr = print.generateExpression();
		var res = expr.eval(ectx);

		assertInstanceOf(BasicObject.class, res);

		BasicObject o = (BasicObject) res;

		assertEquals("BT_Numeric", o.getType().getName());
		assertEquals(17, (Integer)o.getValue());
	}

	@Test
	public void testRecursionAbstraction() {
		TypeContext tctx = StlTypeContext.getInstance().createSubContext();
		ExecutionContext ectx = StlExecutionContext.getInstance().createSubContext();

		// (defn f :X [a :X] (if (= 0 a) 1 (* a (f (- a 1)))))

		Type t1 = TypeTable.getInstance().createVaryingType();
		Type t2 = TypeTable.getInstance().createVaryingType();

		VariableNode fname = new VariableNode("f", t2);
		VariableNode arg = new VariableNode("a", t1);

		ConstantNode ac0 = new ConstantNode("if");
		ConstantNode ac1 = new ConstantNode("=");
		ConstantNode ac2 = new ConstantNode("0");
		ConstantNode ac3 = new ConstantNode("a");
		ConstantNode ac4 = new ConstantNode("1");
		ConstantNode ac5 = new ConstantNode("*");
		ConstantNode ac6 = new ConstantNode("a");
		ConstantNode ac7 = new ConstantNode("f");
		ConstantNode ac8 = new ConstantNode("-");
		ConstantNode ac9 = new ConstantNode("a");
		ConstantNode ac10 = new ConstantNode("1");

		ApplicationNode aap0 = new ApplicationNode();
		aap0.addChild(ac8);
		aap0.addChild(ac9);
		aap0.addChild(ac10);

		ApplicationNode aap1 = new ApplicationNode();
		aap1.addChild(ac7);
		aap1.addChild(aap0);

		ApplicationNode aap2 = new ApplicationNode();
		aap2.addChild(ac5);
		aap2.addChild(ac6);
		aap2.addChild(aap1);

		ApplicationNode aap3 = new ApplicationNode();
		aap3.addChild(ac1);
		aap3.addChild(ac2);
		aap3.addChild(ac3);

		ApplicationNode aap4 = new ApplicationNode();
		aap4.addChild(ac0);
		aap4.addChild(aap3);
		aap4.addChild(ac4);
		aap4.addChild(aap2);

		AbstractionNode abs = new AbstractionNode();
		abs.addChild(fname);
		abs.addChild(arg);
		abs.addChild(aap4);

		abs.inferTypes(tctx);
		abs.updateTypes(tctx);
		abs.generify(tctx);
		var aExpr = abs.generateExpression();
		aExpr.eval(ectx);

		// (f 5)

		ConstantNode c1 = new ConstantNode("f");
		ConstantNode c2 = new ConstantNode("5");

		ApplicationNode app = new ApplicationNode();
		app.addChild(c1);
		app.addChild(c2);

		app.inferTypes(tctx);
		app.updateTypes(tctx);
		app.generify(tctx);

		var expr = app.generateExpression();
		var res = expr.eval(ectx);

		assertInstanceOf(BasicObject.class, res);
		BasicObject result = (BasicObject) res;
		assertEquals(120, (Integer) result.getValue());
	}
}
