package ru.nsu.fit.hmtl.source.tree;

import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.lang.LispUDFExpression;
import ru.nsu.fit.hmtl.core.typesystem.TypeUtils;
import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

/**
 * Node representing let-clause.
 */
public class LetNode extends TreeNode {

	private static int cnt;

	public LetNode() {}

	@Override
	protected Type inferTypesInternal(TypeContext ctx) {
		TypeContext subContext = ctx.createSubContext();

		TreeNode def = children.get(0);
		TreeNode val = children.get(1);
		TreeNode body = children.get(2);

		def.inferTypes(subContext);
		val.inferTypes(subContext);
		TypeUtils.unify(def.getType(), val.getType());

		body.inferTypes(subContext);
		type = body.getType();
		return type;
	}

	@Override
	protected void updateTypesInternal(TypeContext ctx) {
		TypeContext subContext = ctx.createSubContext();

		TreeNode def = children.get(0);
		TreeNode val = children.get(1);
		TreeNode body = children.get(2);

		def.updateTypes(subContext);
		val.updateTypes(subContext);

		TypeUtils.unify(def.getType(), val.getType());

		body.updateTypes(subContext);

		type = TypeUtils.updateType(type);
	}

	@Override
	protected void generifyInternal(TypeContext ctx) {
		TypeContext subContext = ctx.createSubContext();

		TreeNode def = children.get(0);
		TreeNode val = children.get(1);
		TreeNode body = children.get(2);

		def.generify(subContext);
		val.generify(subContext);

		TypeUtils.unify(def.getType(), val.getType());

		body.generify(subContext);

		type = TypeUtils.updateType(type);
	}

	/// Codegen

	@Override
	public Expression generateExpression() {
		LispUDFExpression udfExpression = new LispUDFExpression(type);
		// ExecutionContext ctx1 = ctx.createSubContext();
		VariableNode def = (VariableNode) children.get(0);
		udfExpression.addArg(def.getName(), def.type);
		TreeNode val = children.get(1);
		TreeNode body = children.get(2);
		udfExpression.setBody(body.generateExpression());
		Expression expr = val.generateExpression();

		udfExpression = (LispUDFExpression) udfExpression.apply(expr);

		return udfExpression;
	}

}
