package ru.nsu.fit.hmtl.source.tree;

import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

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
		return TypeTable.getInstance().degenerate(t);
	}

	@Override
	protected void updateTypesInternal(TypeContext ctx) {
	}

}
