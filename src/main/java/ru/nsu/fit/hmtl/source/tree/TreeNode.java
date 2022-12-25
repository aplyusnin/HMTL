package ru.nsu.fit.hmtl.source.tree;

import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;
import ru.nsu.fit.hmtl.source.codegen.SGNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Source node in tree
 */
public abstract class TreeNode implements SGNode {
	// Resulting type of evaluating subtree with given vertex as root
	protected Type type;

	protected final List<TreeNode> children;

	public TreeNode() {
		this.children = new ArrayList<>();
	}

	public void addChild(TreeNode child) {
		children.add(child);
	}

	public Type inferTypes(TypeContext ctx) {
		type = inferTypesInternal(ctx);
		return type;
	}

	protected abstract Type inferTypesInternal(TypeContext ctx);

	/**
	 * Updating types to be the most common types.
	 * @param ctx - of subtree
	 */
	public void updateTypes(TypeContext ctx) {
		updateTypesInternal(ctx);
	}

	/**
	 * Internal updating of node types.
	 * @param ctx - of subtree
	 */
	protected abstract void updateTypesInternal(TypeContext ctx);

	/**
	 * Replace all varying types inside subtree to corresponding generic types.
	 */
	public void generify(TypeContext ctx) {
		generifyInternal(ctx);
	}

	/**
	 * Internal generification of node
	 */
	protected abstract void generifyInternal(TypeContext ctx);

	public Type getType() {
		return type;
	}


}
