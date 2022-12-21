package ru.nsu.fit.hmtl.source.tree;

import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.typesystem.TypeUtils;
import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;
import ru.nsu.fit.hmtl.source.codegen.builders.FunctionBuilder;

/**
 * Node representing let-clause.
 */
public class LetNode extends TreeNode {

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
	public void generateSource(FunctionBuilder fb, ExecutionContext ctx) {
	}

}
