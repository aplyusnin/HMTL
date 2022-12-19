package ru.nsu.fit.hmtl.source.tree;

import ru.nsu.fit.hmtl.inference.context.Context;
import ru.nsu.fit.hmtl.inference.typesystem.types.Type;

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

	public Type inferTypes(Context ctx) {
		type = inferTypesInternal(ctx);
		return type;
	}

	public void addChild(TreeNode child) {
		children.add(child);
	}

	protected abstract Type inferTypesInternal(Context ctx);

	public void updateTypes(Context ctx) {
		updateTypesInternal(ctx);
	}

	protected abstract void updateTypesInternal(Context ctx);
}
