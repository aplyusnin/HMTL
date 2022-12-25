package ru.nsu.fit.hmtl.core.lang;

import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

public class LispIdentifier implements Expression {

	private final ExecutionContext ctx;
	private final String name;
	private final Type type;

	public LispIdentifier(String name, ExecutionContext ctx, Type t) {
		this.name = name;
		this.ctx = ctx;
		this.type = t;
	}

	@Override
	public Expression eval() {
		Expression expr = ctx.lookup(name);
		expr.setType(type);
		return expr.eval();
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Expression deepCopy() {
		return this;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

}
