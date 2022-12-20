package ru.nsu.fit.hmtl.core;

import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Immutable intermediate result of expression.
 */
public class Expression {

	private final Applicable core;
	private final ExecutionContext ctx;

	private TypedObject result;

	// expression may be a partially applied function, so it needs to store remain args
	private final List<String> names;
	private final List<Type> types;
	private int id;

	private Expression(Applicable core, TypedObject result, ExecutionContext ctx, List<String> names, List<Type> types) {
		this.core = core;
		this.result = result;
		this.ctx = ctx;

		this.names = names;
		this.types = types;
		id = 0;
	}


	public Type getType() {
		return null;
	}

	/**
	 * Evaluate result value of Expression
	 * @return link to self, with evaluated result
	 */
	public Expression eval() {
		// if Expression result is not yet evaluated and can be evaluated
		if (result == null && id == names.size()) {
			result = core.eval(ctx);
		}
		return this;
	}

	private Expression createCopy(ExecutionContext newContext) {
		return new Expression(core, null, newContext, names, types);
	}

	public Expression apply(Expression arg) {
		ExecutionContext subContext = ctx.createSubContext();
		subContext.setValue(names.get(id), arg);
		Expression applied = createCopy(subContext);
		applied.id++;
		return applied;
	}

	/**
	 * Get final result value of Expression
	 */
	public TypedObject getValue() {
		if (result == null) {
			eval();
		}
		if (result == null) {
			return TypedObject.functional(getType());
		}
		return result;
	}

	public static class Builder {
		private Applicable core;

		private TypedObject cachedValue;

		private ExecutionContext context;

		private List<String> names;
		private List<Type> types;

		public Builder() {
			names = new ArrayList<>();
			types = new ArrayList<>();
		}

		public Builder setCore(Applicable core) {
			this.core = core;
			return this;
		}

		public void addName(String name) {
			this.names.add(name);
		}

		public void addType(Type type) {
			this.types.add(type);
		}

		public Builder setCachedValue(TypedObject cachedValue) {
			this.cachedValue = cachedValue;
			return this;
		}



		public Builder setContext(ExecutionContext context) {
			this.context = context;
			return this;
		}

		public Expression build() {
			return new Expression(core, cachedValue, context, names, types);
		}

	}

	public static Expression fromTypedObject(TypedObject object, ExecutionContext context) {
		return new Expression(null, object, context, Collections.emptyList(), Collections.emptyList());
	}
}
