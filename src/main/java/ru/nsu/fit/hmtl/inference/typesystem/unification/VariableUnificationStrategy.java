package ru.nsu.fit.hmtl.inference.typesystem.unification;

import ru.nsu.fit.hmtl.inference.typesystem.TypeInferenceException;
import ru.nsu.fit.hmtl.inference.typesystem.types.Type;

import java.util.Optional;

public class VariableUnificationStrategy implements UnificationStrategy {

	@Override
	public Optional<Type> unifyWith(Type owner, Type other) throws TypeInferenceException {
		if (!other.canSubstitute()) {
			return Optional.empty();
		}
		return Optional.of(other);
	}

	@Override
	public boolean isSubstitutableWith(Type t1, Type t2) {
		return true;
	}
}
