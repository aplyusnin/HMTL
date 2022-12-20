package ru.nsu.fit.hmtl.source.tree;

import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

/**
 * Node representing let-clause.
 */
public class LetNode extends TreeNode {

	public LetNode() {}

	@Override
	protected Type inferTypesInternal(TypeContext ctx) {
		return null;
	}

	@Override
	protected void updateTypesInternal(TypeContext ctx) {
	}

	@Override
	protected void generifyInternal(TypeContext ctx) {
	}

}
