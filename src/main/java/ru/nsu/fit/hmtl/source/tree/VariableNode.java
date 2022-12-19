package ru.nsu.fit.hmtl.source.tree;

import ru.nsu.fit.hmtl.inference.context.Context;
import ru.nsu.fit.hmtl.inference.typesystem.types.Type;

/**
 * Node representing named variable with type.
 */
public class VariableNode extends TreeNode {

	private final String name;

	public VariableNode(String name) {
		this(name, null);
	}

	public VariableNode(String name, Type t) {
		this.name = name;
		this.type = t;
	}

	@Override
	protected Type inferTypesInternal(Context ctx) {
		return null;
	}

	@Override
	protected void updateTypesInternal(Context ctx) {
	}

}
