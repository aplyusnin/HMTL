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
	private int applied;

	private final ExecutionContext ctx;
	private final Type type;

	private Expression body;

	public LispUDFExpression(ExecutionContext ctx, Type type) {
		args = new ArrayList<>();
		this.argsType = new ArrayList<>();
		this.type = type;
		this.ctx = ctx;
		applied = 0;
	}

	@Override
	public Expression eval() {
		if (applied < args.size()) return this;
		return body.eval();
	}

	@Override
	public Expression apply(Expression other) {
		if (applied + 1 > args.size()) {
			throw new InvalidApplicationException(toString(), other.toString(), getType(), other.getType());
		}
		if (!argsType.get(applied).getName().equals(other.getType().getName())) {
			throw new InvalidApplicationException(toString(), other.toString(), getType(), other.getType());
		}
		return applyImmutable(other);
	}

	private Expression applyImmutable(Expression other) {
		LispUDFExpression res = (LispUDFExpression) deepCopy();
		res.ctx.setValue(args.get(applied), other);
		res.applied++;
		return res;
	}

	@Override
	public Type getType() {
		Type val = type;
		for (int i = 0; i < applied; i++) {
			val = ((ApplicationType) val).getRhs();
		}
		return val;
	}

	@Override
	public Expression deepCopy() {
		//ExecutionContext subCtx = ctx.createCopy();
		LispUDFExpression lexpr = new LispUDFExpression(ctx, type);
		lexpr.applied = applied;
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
