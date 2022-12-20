package ru.nsu.fit.hmtl.source.tree;

import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.typesystem.TypeUtils;
import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;
import ru.nsu.fit.hmtl.source.codegen.builders.FunctionBuilder;

/**
 * Node representing named constant.
 */
public class ConstantNode extends TreeNode {

	private final String name;

	public ConstantNode(String name) {
		this.name = name;
	}

	@Override
	protected Type inferTypesInternal(TypeContext ctx) {
		Type t = ctx.lookup(name);
		t = TypeUtils.degenerate(t);
		return t;
	}

	@Override
	protected void updateTypesInternal(TypeContext ctx) {
		type = TypeUtils.updateType(type);
	}

	@Override
	protected void generifyInternal(TypeContext ctx) {
		type =  TypeUtils.generify(type);
	}

	/// Codegen

	@Override
	public void generateSource(FunctionBuilder fb, ExecutionContext ctx) {
	}

}
