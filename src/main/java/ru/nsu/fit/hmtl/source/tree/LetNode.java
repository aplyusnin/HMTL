package ru.nsu.fit.hmtl.source.tree;

import ru.nsu.fit.hmtl.inference.context.Context;
import ru.nsu.fit.hmtl.inference.typesystem.types.Type;

/**
 * Node representing let-clause.
 */
public class LetNode extends TreeNode {

	public LetNode() {}

	@Override
	protected Type inferTypesInternal(Context ctx) {
		return null;
	}

	@Override
	protected void updateTypesInternal(Context ctx) {
	}

}
