package ru.nsu.fit.hmtl.codegen.tree;

import ru.nsu.fit.hmtl.inference.typesystem.types.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Node in the semantic tree.
 */
public abstract class TreeNode {

	private final List<TreeNode> children;

	public TreeNode() {
		children = new ArrayList<>();
	}

	public void addChildren(TreeNode node) {
		children.add(node);
	}

	/**
	 * Get information about children of the node
	 * @return list of children
	 */
	List<TreeNode> getChildren() {
		return children;
	}

	/**
	 * Get expected type of node.
	 * @return type
	 */
	Type getType() {
		return null;
	}

}
