package ru.nsu.fit.hmtl.core.typesystem;

import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.*;

import java.util.ArrayList;
import java.util.List;

public class TypeUtils {

	public boolean canSubstitute(Type t1, Type t2) {
		return t1.equals(t2);
	}

	public static Type unify(Type t1, Type t2) {
		if (t1.isSubstitutable()) {
			return TypeTable.getInstance().equate(t1.getName(), t2.getName());
		}
		if (t2.isSubstitutable()) {
			return TypeTable.getInstance().equate(t2.getName(), t1.getName());
		}

		if (t1.isComplex() != t2.isComplex()) {
			throw new RuntimeException("Incompatible types: " + t1.getName() + " and " + t2.getName());
		}

		if (!t1.isComplex()) {
			return TypeTable.getInstance().equate(t1.getName(), t2.getName());
		}

		List<Type> lt1 = t1.getInternalTypes();
		List<Type> lt2 = t2.getInternalTypes();
		if (lt1.size() != lt2.size()) throw new RuntimeException("Incompatible types");
		List<Type> lt3 = new ArrayList<>(lt1.size());
		for (int i = 0; i < lt1.size(); i++) {
			lt3.add(unify(lt1.get(i), lt2.get(i)));
		}
		if (lt1.size() == 1) {
			return new ListType(lt3.get(0));
		}
		else {
			return new ApplicationType(lt3.get(0), lt3.get(1));
		}
	}

	public static Type apply(Type at, Type val) {
		if (at instanceof BasicType) {
			throw new RuntimeException("Cannot cast basic type to functional");
		}
		if (at instanceof ListType) {
			throw new RuntimeException("Cannot cast list type to functional");
		}
		if (at instanceof GenericType) {
			throw new RuntimeException("Unexpected generic type");
		}
		if (at instanceof VaryingType) {
			at = TypeTable.getInstance().createApplicationType(at);
		}

		unify(((ApplicationType) at).getLhs(), val);
		return ((ApplicationType) at).getRhs();
	}

	public static Type updateType(Type t) {
		if (t instanceof ApplicationType) {
			return new ApplicationType(updateType(((ApplicationType) t).getLhs()), updateType(((ApplicationType) t).getRhs()));
		}
		if (t instanceof ListType) {
			return new ListType(updateType(((ListType) t).getCore()));
		}
		return TypeTable.getInstance().getType(t.getName());
	}

	public static Type degenerate(Type t) {
		return TypeTable.getInstance().degenerate(t);
	}

	public static Type generify(Type t) {
		return TypeTable.getInstance().generify(t);
	}
}
