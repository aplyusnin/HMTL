package ru.nsu.fit.hmtl.core.typesystem;

import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeUtils {

	public static Type unify(Type t1, Type t2) {
		t1 = TypeTable.getInstance().getType(t1.getName());
		t2 = TypeTable.getInstance().getType(t2.getName());

		if (t1.isSubstitutable()) {
			return TypeTable.getInstance().equate(t1.getName(), t2.getName());
		}
		if (t2.isSubstitutable()) {
			return TypeTable.getInstance().equate(t2.getName(), t1.getName());
		}

		if (t1.isComplex() != t2.isComplex()) {
			throw new TypeInferenceException("Incompatible types: " + t1.getName() + " and " + t2.getName());
		}

		if (!t1.isComplex()) {
			return TypeTable.getInstance().equate(t1.getName(), t2.getName());
		}

		List<Type> lt1 = t1.getInternalTypes();
		List<Type> lt2 = t2.getInternalTypes();
		if (lt1.size() != lt2.size())
			throw new TypeInferenceException("Incompatible types: " + t1.getName() + " and " + t2.getName());
		List<Type> lt3 = new ArrayList<>(lt1.size());
		for (int i = 0; i < lt1.size(); i++) {
			lt3.add(unify(lt1.get(i), lt2.get(i)));
		}
		if (lt1.size() == 1) {
			Type lt = new ListType(TypeTable.getInstance().getType(lt3.get(0).getName()));

			TypeTable.getInstance().registerFinalType(lt);
			return lt;
		}
		else {
			Type ap = new ApplicationType(
					TypeTable.getInstance().getType(lt3.get(0).getName()),
					TypeTable.getInstance().getType(lt3.get(1).getName()));

			TypeTable.getInstance().registerFinalType(ap);

			return ap;
		}
	}

	public static Type apply(Type at, Type val) {
		at = updateType(at);
		val = updateType(val);
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
			Type val = new ApplicationType(
					updateType(((ApplicationType) t).getLhs()),
					updateType(((ApplicationType) t).getRhs()));

			TypeTable.getInstance().registerFinalType(val);

			return val;
		}
		if (t instanceof ListType) {
			Type val = new ListType(updateType(((ListType) t).getCore()));

			TypeTable.getInstance().registerFinalType(val);

			return val;
		}
		return TypeTable.getInstance().getType(t.getName());
	}

	public static Type degenerate(Type t) {
		return TypeTable.getInstance().degenerate(t);
	}

	public static Type generify(Type t) {
		return TypeTable.getInstance().generify(t);
	}


	public static String generatePrettyName(Type t) {
		Map<String, String> nameTable = new HashMap<>();
		StringBuilder nameBuilder = new StringBuilder();
		generatePrettyNameInternal(t, nameTable, nameBuilder);
		return nameBuilder.toString();
	}

	private static void generatePrettyNameInternal(Type t, Map<String, String> nameTable, StringBuilder builder) {
		if (t instanceof BasicType) {
			builder.append(t.getName().substring(3));
			return;
		}
		if (t instanceof GenericType) {
			if (!nameTable.containsKey(t.getName())) {
				nameTable.put(t.getName(), "T" + nameTable.size());
			}
			builder.append(nameTable.get(t.getName()));
			return;
		}
		if (t instanceof ListType) {
			builder.append("[");
			generatePrettyNameInternal(((ListType) t).getCore(), nameTable, builder);
			builder.append("]");
			return;
		}
		if (t instanceof ApplicationType) {
			ApplicationType at = (ApplicationType) t;
			builder.append("(");
			generatePrettyNameInternal(at.getLhs(), nameTable, builder);
			builder.append(" --> ");
			generatePrettyNameInternal(at.getRhs(), nameTable, builder);
			builder.append(")");
			return;
		}
		throw new RuntimeException("Unexpected type");
	}
}
