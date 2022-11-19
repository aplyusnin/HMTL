package ru.nsu.fit.hmtl.inference.typesystem.unification;

import ru.nsu.fit.hmtl.inference.typesystem.TypeInferenceException;
import ru.nsu.fit.hmtl.inference.typesystem.types.Type;

import java.util.Optional;

public class ConstantUnificationStrategy implements UnificationStrategy {

	@Override
	public Optional<Type> unifyWith(Type owner, Type other) throws TypeInferenceException {
		// If types already the same
		if (owner.getId() == other.getId()) {
			return Optional.of(other);
		}
		// Otherwise, owner type cannot be unified with other type
		return Optional.empty();
	}

	@Override
	public boolean isSubstitutableWith(Type t1, Type t2) {
		return t1.getId() == t2.getId();
	}
}
