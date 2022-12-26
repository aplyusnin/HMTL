package ru.nsu.fit.hmtl.source.tree;

import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.typesystem.TypeUtils;
import ru.nsu.fit.hmtl.core.typesystem.context.TypeContext;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

/**
 * Node representing named variable with type.
 */
public class VariableNode extends TreeNode {

	private final String name;

	public VariableNode(String name) {
		this(name, TypeTable.getInstance().createVaryingType());
	}

	public VariableNode(String name, Type t) {
		this.name = name;
		this.type = t;
	}

	@Override
	protected Type inferTypesInternal(TypeContext ctx) {
		ctx.setType(name, type);
		return type;
	}

	@Override
	protected void updateTypesInternal(TypeContext ctx) {
		type = TypeUtils.updateType(type);
		ctx.setType(name, type);
	}

	@Override
	protected void generifyInternal(TypeContext ctx) {
		type = TypeUtils.generify(type);
		ctx.setType(name, type);
	}

	public String getName() {
		return name;
	}

	/// Codegen

	@Override
	public Expression generateExpression() {
		throw new RuntimeException("Variable declaration does not produce any expression");
	}

}
