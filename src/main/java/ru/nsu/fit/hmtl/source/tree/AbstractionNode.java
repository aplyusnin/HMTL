package ru.nsu.fit.hmtl.source.tree;

import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.lang.LispDefExpression;
import ru.nsu.fit.hmtl.core.lang.LispUDFExpression;
import ru.nsu.fit.hmtl.core.typesystem.TypeUtils;
import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.ApplicationType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

/**
 * Node representing function creation.
 */
public class AbstractionNode extends TreeNode {

	public AbstractionNode() {}

	@Override
	protected Type inferTypesInternal(TypeContext ctx) {
		VariableNode fdef = (VariableNode) children.get(0);

		Type resType = fdef.getType();
		Type fType = resType;
		TypeContext subCtx = ctx.createSubContext();
		for (int i = children.size() - 2; i > 0; i--) {
			Type arg = children.get(i).inferTypes(subCtx);
			fType = new ApplicationType(arg, fType);
			TypeTable.getInstance().registerFinalType(fType);
		}

		ctx.setType(fdef.getName(), fType);

		Type res = children.get(children.size() - 1).inferTypes(subCtx);

		TypeUtils.unify(resType, res);

		return fType;
	}

	@Override
	protected void updateTypesInternal(TypeContext ctx) {
		type = TypeUtils.updateType(type);

		children.get(0).updateTypes(ctx);
		TypeContext subCtx = ctx.createSubContext();
		for (int i = 1; i < children.size(); i++) {
			children.get(i).updateTypes(subCtx);
		}
	}

	@Override
	protected void generifyInternal(TypeContext ctx) {
		type = TypeUtils.generify(type);

		ctx.setType(((VariableNode)children.get(0)).getName(), type);
		TypeContext subCtx = ctx.createSubContext();
		for (int i = 1; i < children.size(); i++) {
			children.get(i).generify(subCtx);
		}
	}

	/// Codegen

	@Override
	public Expression generateExpression() {

		VariableNode fdef = (VariableNode) children.get(0);
		LispUDFExpression udfExpression = new LispUDFExpression(type);

		for (int i = 1; i + 1 < children.size(); i++) {
			VariableNode vnode = (VariableNode) children.get(i);
			udfExpression.addArg(vnode.getName(), vnode.type);
		}

		udfExpression.setBody(children.get(children.size() - 1).generateExpression());

		return new LispDefExpression(fdef.getName(), udfExpression);
	}

}
