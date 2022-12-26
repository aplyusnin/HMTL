package ru.nsu.fit.hmtl.core.lang;

import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.typesystem.types.ApplicationType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.ArrayList;
import java.util.List;

public class LispApplyExpression implements Expression {

	private Type type;
	private Expression body;
	private final List<Expression> applied;

	public LispApplyExpression(Expression body) {
		this.body = body;
		this.type = body.getType();
		this.applied = new ArrayList<>();
	}

	@Override
	public Expression eval(ExecutionContext ctx) {
		Expression func = body.eval(ctx);

		for (var arg : applied) {
			func = func.apply(arg);
		}

		return func.eval(ctx);
	}

	@Override
	public Expression apply(Expression other) {
		return applyImmutable(other);
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public Expression deepCopy() {
		try {
			LispApplyExpression f = new LispApplyExpression(body);
			for (var a : applied) {
				f.applied.add(a.deepCopy());
			}
			return f;
		} catch (Exception e) {
			throw new RuntimeException("Could not instantiate copy");
		}
	}

	private LispApplyExpression applyImmutable(Expression other) {
		LispApplyExpression func = (LispApplyExpression) deepCopy();
		func.applied.add(other);
		func.type = ((ApplicationType)type).getRhs();
		return func;
	}

}
