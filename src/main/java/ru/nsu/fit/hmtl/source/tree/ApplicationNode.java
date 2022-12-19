package ru.nsu.fit.hmtl.source.tree;

import ru.nsu.fit.hmtl.inference.context.Context;
import ru.nsu.fit.hmtl.inference.typesystem.types.Type;

/**
 * Node representing application of function.
 */
public class ApplicationNode extends TreeNode {

	public ApplicationNode() {}

	@Override
	protected Type inferTypesInternal(Context ctx) {
		return null;
	}

	@Override
	protected void updateTypesInternal(Context ctx){
	}

}
