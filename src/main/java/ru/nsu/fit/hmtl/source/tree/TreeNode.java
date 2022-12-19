package ru.nsu.fit.hmtl.source.tree;

import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Source node in tree
 */
public abstract class TreeNode {
	// Resulting type of evaluating subtree with given vertex as root
	protected Type type;

	protected final List<TreeNode> children;

	public TreeNode() {
		this.children = new ArrayList<>();
	}

	public Type inferTypes(TypeContext ctx) {
		type = inferTypesInternal(ctx);
		return type;
	}

	public void addChild(TreeNode child) {
		children.add(child);
	}

	protected abstract Type inferTypesInternal(TypeContext ctx);

	public void updateTypes(TypeContext ctx) {
		updateTypesInternal(ctx);
	}

	protected abstract void updateTypesInternal(TypeContext ctx);

	public Type getType() {
		return type;
	}
}
