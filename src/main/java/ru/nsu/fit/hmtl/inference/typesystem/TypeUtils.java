package ru.nsu.fit.hmtl.inference.typesystem;

import ru.nsu.fit.hmtl.inference.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.inference.typesystem.table.TypeTableImpl;
import ru.nsu.fit.hmtl.inference.typesystem.types.DefaultTypeBuilder;
import ru.nsu.fit.hmtl.inference.typesystem.types.Type;

import java.util.Optional;

public class TypeUtils {

	/**
	 * Create an abstraction of types.
	 * @param left - left-hand side
	 * @param right - right-hand side
	 * @return new Type equals to lhs -> rhs
	 */
	public static int abstraction(Type left, Type right) {
		return TypeTableImpl.getInstance().createAndRegisterType(new DefaultTypeBuilder().setLeftHandSide(left).setRightHandSide(right));
	}

	/**
	 * Applies type t to the init type.
	 * @param init - initial type
	 * @param t - applied type
	 * @throws TypeInferenceException - if t doesn't match init's lhs
	 */
	public static int application(Type init, Type t) throws TypeInferenceException {

		if (!init.isComplex()) {
			Type abs = createAbstraction();
			unify(init, abs);
			unify(abs.getLhs(), t);
			return abs.getRhs().getId();
		}

		if (!init.getUnificationStrategy().isSubstitutableWith(init, t)) {
			throw new TypeInferenceException("Type mismatch during an application");
		}
		return init.getRhs().getId();
	}

	public static Type createAbstraction() {
		int lId = TypeTableImpl.getInstance().createAndRegisterType(new DefaultTypeBuilder());
		Type lt = TypeTableImpl.getInstance().getTypeByID(lId);
		int rId = TypeTableImpl.getInstance().createAndRegisterType(new DefaultTypeBuilder());
		Type rt = TypeTableImpl.getInstance().getTypeByID(rId);
		int resId = TypeTableImpl.getInstance().createAndRegisterType(new DefaultTypeBuilder().setLeftHandSide(lt).setLeftHandSide(rt));
		return TypeTableImpl.getInstance().getTypeByID(resId);
	}

	public static Type unify(Type t1, Type t2) throws TypeInferenceException {
		TypeTableImpl.getInstance().unifyTypes(t1.getId(), t2.getId());
		return TypeTableImpl.getInstance().getTypeByID(t1.getId());
	}

	public static boolean substitutable(Type t1, Type t2) {
		return t1.getUnificationStrategy().isSubstitutableWith(t1, t2);
	}

	/*
	  Check if t1 can be substituted by t2
	  @param t1 - type to be substituted
	 * @param t2 - type for substitution
	 * @return if t1 can be substituted with t2
	 */
	/*public static boolean substitutable(Type t1, Type t2) {
		// type can always be substituted by itself
		if (t1.equals(t2)) {
			return true;
		}
		// tif t1 cannot be substituted or t2 cannot substitute
		if (!t1.isSubstitutable() || !t2.canSubstitute()) {
			return false;
		}
		// if t1 is of an applicative form
		if (t1.isComplex()) {
			// t2 should also be
			if (t2.isComplex()) {
				// check if both sides are match
				return substitutable(t1.getLeftHandSide(), t2.getLeftHandSide()) && substitutable(t1.getRightHandSide(), t2.getRightHandSide());
			} else {
				return false;
			}
		}
		// otherwise, t1 can always be changed to t2
		return true;
	}*/

	/*public static Type merge(Type t1, Type t2) throws TypeInferenceException {
		if (t1.isComplex()) {
			if (!t2.isComplex()) {
				throw new TypeInferenceException("Cannot merge types: " + t1.getName() + " and " + t2.getName());
			}
			Type tl = merge(t1.getLeftHandSide(), t2.getLeftHandSide());
			Type tr = merge(t1.getRightHandSide(), t2.getRightHandSide());
			return new DefaultTypeBuilder().setLeftHandSide(tl).setRightHandSide(tr).build();
		} else {
			if (t2.isComplex()) {
				if (t1.isSubstitutable()) {
					return t2;
				} else {
					throw new TypeInferenceException("Cannot replace type: " + t1.getName() + " with " + t2.getName());
				}
			} else {
				Type tl = TypeTable.getInstance().getTypeByID(t1.getId());
				Type tr = TypeTable.getInstance().getTypeByID(t2.getId());
				if (tl.getId() == tr.getId()) {
					return tl;
				}
				if (tl.isVariable() && tr.isVariable()) {
					int id = TypeTable.getInstance().join(tl.getId(), tr.getId());
					return TypeTable.getInstance().getRealType(id);
				}
				if (tl.isBasic() && tr.isBasic()) {
					throw new TypeInferenceException("Cannot replace type: " + t1.getName() + " with " + t2.getName());
				} else {
					if (tl.isBasic()) return tl;
					else return tr;
				}
			}
		}
	}*/
}
