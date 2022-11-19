package ru.nsu.fit.hmtl.inference.typesystem.unification;

import ru.nsu.fit.hmtl.inference.typesystem.TypeInferenceException;
import ru.nsu.fit.hmtl.inference.typesystem.types.Type;

import java.util.Optional;

public class EmptyTypeUnificationStrategy implements UnificationStrategy {

	@Override
	public Optional<Type> unifyWith(Type owner, Type other) throws TypeInferenceException {
		if (other.getId() == owner.getId()) {
			return Optional.of(other);
		}
		return Optional.empty();
	}

	@Override
	public boolean isSubstitutableWith(Type t1, Type t2) {
		return t1.getId() == t2.getId();
	}
}
