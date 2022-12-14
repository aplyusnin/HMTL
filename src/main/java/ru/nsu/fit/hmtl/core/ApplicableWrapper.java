package ru.nsu.fit.hmtl.core;

import ru.nsu.fit.hmtl.inference.typesystem.types.Type;

/**
 * Immutable wrapper over {@link Applicable} that supports partial application.
 */
public class ApplicableWrapper {

	private final Applicable core;

	private TypedObject cache;
	private final WrapperList args;

	private ApplicableWrapper(Applicable core, TypedObject cache) {
		this.core = core;
		this.cache = cache;
		this.args = new WrapperList();
	}


	public Type getType() {
		return null;
	}

	public TypedObject eval() {
		if (cache != null) {
			return cache;
		}
		// core.getArgsInfo().validate(args);
		cache = core.apply(args);
		return cache;
	}

	private ApplicableWrapper createCopy() {
		ApplicableWrapper copy = new ApplicableWrapper(core, null);
		for (var x : args) {
			copy.args.add(x);
		}
		return copy;
	}

	public ApplicableWrapper apply(ApplicableWrapper arg) {
		ApplicableWrapper applied = createCopy();
		applied.args.add(arg);
		return applied;
	}


	public static class Builder {
		private Applicable core;

		private TypedObject cachedValue;

		public Builder setCore(Applicable core) {
			this.core = core;
			return this;
		}

		public Builder setCachedValue(TypedObject cachedValue) {
			this.cachedValue = cachedValue;
			return this;
		}

		public ApplicableWrapper build() {
			return new ApplicableWrapper(core, cachedValue);
		}

	}

	public static ApplicableWrapper fromTypedObject(TypedObject object) {
		return new ApplicableWrapper(null, object);
	}
}
