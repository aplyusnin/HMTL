package ru.nsu.fit.hmtl.core.lang;

import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.InvalidApplicationException;
import ru.nsu.fit.hmtl.core.typesystem.types.ApplicationType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.ArrayList;
import java.util.List;

public class LispUDFExpression implements Expression {
	private final List<String> args;
	private final List<Type> argsType;
	private final List<Expression> applied;

	private final Type type;

	private Expression body;

	public LispUDFExpression(Type type) {
		args = new ArrayList<>();
		this.argsType = new ArrayList<>();
		this.type = type;
		applied = new ArrayList<>();
	}

	@Override
	public Expression eval(ExecutionContext ctx) {
		if (applied.size() < args.size()) return this;
		ExecutionContext copy = ctx.createSubContext();

		for (int i = 0; i < args.size(); i++) {
			copy.setValue(args.get(i), applied.get(i).eval(copy));
		}

		return body.eval(copy);
	}

	@Override
	public Expression apply(Expression other) {
		int id = applied.size();
		if (id + 1 > args.size()) {
			throw new InvalidApplicationException(toString(), other.toString(), getType(), other.getType());
		}
		if (!argsType.get(id).getName().equals(other.getType().getName())) {
			throw new InvalidApplicationException(toString(), other.toString(), getType(), other.getType());
		}
		return applyImmutable(other);
	}

	private Expression applyImmutable(Expression other) {
		LispUDFExpression res = (LispUDFExpression) deepCopy();
		res.applied.add(other);
		return res;
	}

	@Override
	public Type getType() {
		Type val = type;
		for (int i = 0; i < applied.size(); i++) {
			val = ((ApplicationType) val).getRhs();
		}
		return val;
	}

	@Override
	public Expression deepCopy() {
		//ExecutionContext subCtx = ctx.createCopy();
		LispUDFExpression lexpr = new LispUDFExpression(type);
		lexpr.applied.addAll(applied);
		lexpr.body = body;
		lexpr.argsType.addAll(argsType);
		lexpr.args.addAll(args);
		return lexpr;
	}

	public void addArg(String argName, Type argType) {
		args.add(argName);
		argsType.add(argType);
	}

	public void setBody(Expression expr) {
		this.body = expr;
	}

}
