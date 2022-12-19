package ru.nsu.fit.hmtl.source.tree;

import ru.nsu.fit.hmtl.core.typesystem.TypeUtils;
import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

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
	protected Type inferTypesInternal(TypeContext ctx) {
		return type;
	}

	@Override
	protected void updateTypesInternal(TypeContext ctx) {
		type = TypeUtils.updateType(type);
	}

	public String getName() {
		return name;
	}

}
