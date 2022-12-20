package ru.nsu.fit.hmtl.source.codegen;

import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.source.codegen.builders.FunctionBuilder;
import ru.nsu.fit.hmtl.source.tree.TreeNode;

/**
 * Class for calling codegen for the given Builder.
 */
public class Traverser {
	private final SGNode root;
	private final FunctionBuilder builder;
	private final ExecutionContext ctx;

	public Traverser(SGNode root, FunctionBuilder builder, ExecutionContext ctx) {
		this.root = root;
		this.builder = builder;
		this.ctx = ctx;
	}

	public void generateSource() {
		root.generateSource(builder, ctx);
		builder.make();
	}

}
