package ru.nsu.fit.hmtl.source.tree;

import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.lang.LispApplyExpression;
import ru.nsu.fit.hmtl.core.typesystem.TypeUtils;
import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Node representing application of function.
 */
public class ApplicationNode extends TreeNode {

	public ApplicationNode() {}

	@Override
	protected Type inferTypesInternal(TypeContext ctx) {
		List<Type> types = new ArrayList<>(children.size());

		for (var c : children) {
			types.add(c.inferTypes(ctx));
		}

		Type current = types.get(0);

		for (int i = 1; i < types.size(); i++) {
			current = TypeUtils.apply(current, types.get(i));
		}

		return current;
	}

	@Override
	protected void updateTypesInternal(TypeContext ctx) {
		type = TypeUtils.updateType(type);
		for (var c : children) {
			c.updateTypes(ctx);
		}
	}

	@Override
	protected void generifyInternal(TypeContext ctx) {
		type = TypeUtils.generify(type);
		for (var c : children) {
			c.generify(ctx);
		}
	}

	/// Codegen

	@Override
	public Expression generateExpression() {
		List<Expression> exprs = new ArrayList<>(children.size());

		for (var c : children) {
			exprs.add(c.generateExpression());
		}

		Expression aExpr = new LispApplyExpression(exprs.get(0));

		for (int i = 1; i < exprs.size(); i++) {
			aExpr = aExpr.apply(exprs.get(i));
		}

		return aExpr;
	}

}
