package ru.nsu.fit.hmtl.source.tree;

import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.typesystem.TypeUtils;
import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.core.typesystem.types.ApplicationType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;
import ru.nsu.fit.hmtl.source.codegen.builders.FunctionBuilder;

/**
 * Node representing function creation.
 */
public class AbstractionNode extends TreeNode {

	public AbstractionNode() {}

	@Override
	protected Type inferTypesInternal(TypeContext ctx) {
		VariableNode fdef = (VariableNode) children.get(0);
		ctx.setType(fdef.getName(), fdef.inferTypes(ctx));

		TypeContext subCtx = ctx.createSubContext();
		for (int i = 1; i + 1 < children.size(); i++) {
			VariableNode tmp = (VariableNode) children.get(i);
			subCtx.setType(tmp.getName(), tmp.inferTypes(subCtx));
		}

		Type res = children.get(children.size() - 1).inferTypes(ctx);

		TypeUtils.unify(fdef.getType(), res);

		Type rval = res;

		for (int i = children.size() - 2; i > 0; i--) {
			rval = new ApplicationType(children.get(i).getType(), rval);
		}

		return rval;
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
	public void generateSource(FunctionBuilder fb, ExecutionContext ctx) {
	}

}
