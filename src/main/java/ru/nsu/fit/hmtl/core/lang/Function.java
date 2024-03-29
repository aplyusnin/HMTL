package ru.nsu.fit.hmtl.core.lang;

import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.InvalidApplicationException;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.ArrayList;
import java.util.List;

public abstract class Function implements Expression {

	protected final List<Expression> applied;
	protected Type type;

	public Function() {
		applied = new ArrayList<>();
	}

	@Override
	public Expression apply(Expression other) {
		var types = getArgTypes();
		int id = applied.size();
		if (id + 1 > types.size()) {
			throw new InvalidApplicationException(toString(), other.toString(), getType(), other.getType());
		}
		/*if (types.get(id) instanceof GenericType) {
			return applyImmutable(other);
		}
		if (!types.get(id).getName().equals(other.getType().getName())) {
			throw new InvalidApplicationException(toString(), other.toString(), getType(), other.getType());
		}*/
		return applyImmutable(other);
	}

	@Override
	public Expression eval(ExecutionContext ctx) {
		if (applied.size() < getArgTypes().size()) return this;
		return evalInternal(ctx);
	}

	protected abstract Expression evalInternal(ExecutionContext ctx);

	public abstract List<Type> getArgTypes();

	@Override
	public Type getType() {
		/*Type val = type;
		for (int i = 0; i < applied.size(); i++) {
			val = ((ApplicationType) val).getRhs();
		}*/
		return type;
	}

	@Override
	public void setType(Type type) {
		this.type = type;
	}

	@Override
	@SuppressWarnings("deprecation")
	public Expression deepCopy() {
		try {
			Function f = this.getClass().newInstance();
			for (var a : applied) {
				f.applied.add(a.deepCopy());
			}
			return f;
		} catch (Exception e) {
			throw new RuntimeException("Could not instantiate copy");
		}
	}

	private Function applyImmutable(Expression other) {
		Function func = (Function) deepCopy();
		func.applied.add(other);
		return func;
	}
}
