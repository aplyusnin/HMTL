package ru.nsu.fit.hmtl.core.lang;

import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

public class LispDefExpression implements Expression {
	private Expression body;
	private String name;
	public LispDefExpression(String name, Expression body) {
		this.body = body;
		this.name = name;
	}

	@Override
	public Expression eval(ExecutionContext ctx) {
		ctx.setValue(name, body);
		return body;
	}

	@Override
	public Expression apply(Expression other) {
		throw new RuntimeException("Cannot apply to def");
	}

	@Override
	public Type getType() {
		return body.getType();
	}

	@Override
	public void setType(Type type) {
	}

	@Override
	public Expression deepCopy() {
		return new LispDefExpression(name, body);
	}

}


// (let f (defn x a (+ a 1)) (f 2))