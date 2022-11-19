package ru.nsu.fit.hmtl.inference.typesystem.unification;

import ru.nsu.fit.hmtl.inference.typesystem.TypeInferenceException;
import ru.nsu.fit.hmtl.inference.typesystem.TypeUtils;
import ru.nsu.fit.hmtl.inference.typesystem.table.TypeTableImpl;
import ru.nsu.fit.hmtl.inference.typesystem.types.Type;

import java.util.Optional;

public class ComplexUnificationStrategy implements UnificationStrategy {

	@Override
	public Optional<Type> unifyWith(Type owner, Type other) throws TypeInferenceException {
		// If types are already the same
		if (other.getId() == owner.getId()) {
			return Optional.of(owner);
		}
		// if other type is not abstraction over some variable, then unification is not possible
		if (!other.isComplex()) {
			return Optional.empty();
		}

		Type lhs = TypeUtils.unify(owner.getLhs(), other.getLhs());
		Type rhs = TypeUtils.unify(owner.getRhs(), other.getRhs());

		int resId = TypeUtils.abstraction(lhs, rhs);

 		return Optional.of(TypeTableImpl.getInstance().getTypeByID(resId));
	}

	@Override
	public boolean isSubstitutableWith(Type t1, Type t2) {
		if (t1.getId() == t2.getId()) {
			return true;
		}
		if (!t2.isComplex()) {
			return false;
		}
		return t1.getLhs().getUnificationStrategy().isSubstitutableWith(t1.getLhs(), t2.getLhs()) &&
				t1.getRhs().getUnificationStrategy().isSubstitutableWith(t1.getRhs(), t2.getRhs());
	}
}
