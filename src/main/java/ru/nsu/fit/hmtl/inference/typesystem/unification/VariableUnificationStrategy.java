package ru.nsu.fit.hmtl.inference.typesystem.unification;

import ru.nsu.fit.hmtl.inference.typesystem.TypeInferenceException;
import ru.nsu.fit.hmtl.inference.typesystem.types.Type;

import java.util.Optional;

/**
 * Unification strategy for varying types.
 */
public class VariableUnificationStrategy implements UnificationStrategy {

	private final static UnificationStrategy instance = new VariableUnificationStrategy();

	public static UnificationStrategy getInstance() {
		return instance;
	}

	@Override
	public Optional<Type> unifyWith(Type owner, Type other) throws TypeInferenceException {
		if (!other.canSubstitute()) {
			return Optional.empty();
		}
		return Optional.of(other);
	}

	@Override
	public boolean isSubstitutableWith(Type t1, Type t2) {
		return t2.canSubstitute();
	}
}
