package ru.nsu.fit.hmtl.core.typesystem.table;

import ru.nsu.fit.hmtl.core.typesystem.TypeInferenceException;
import ru.nsu.fit.hmtl.core.typesystem.types.*;

import java.util.HashMap;
import java.util.Map;

/**
 * A type table for processing type joining
 */
public class TypeTable {

	// Make this implementation singleton to have access everywhere
	private static TypeTable instance = null;

	public static TypeTable getInstance() {
		if (instance == null) {
			instance = new TypeTable();
		}
		return instance;
	}

	private TypeTable() {
		this.typesClasses = new HashMap<>();
		createBasicType("Numeric", Integer.class);
		createBasicType("Bool", Boolean.class);
		createBasicType("Byte", Byte.class);
		createBasicType("Char", Character.class);
	}

	private final Map<String, EquivalenceClass> typesClasses;
	private int varTypes = 0;
	private int genericTypes = 0;

	public Type createBasicType(String name, Class<?> javaClass) {
		BasicType type = new BasicType(name, javaClass);
		typesClasses.put(type.getName(), new EquivalenceClass(true, type));
		return type;
	}


	public Type createGenericType() {
		GenericType type = new GenericType(genericTypes);
		genericTypes++;
		typesClasses.put(type.getName(), new EquivalenceClass(true, type));
		return type;
	}

	public Type createVaryingType() {
		VaryingType type = new VaryingType(varTypes);
		varTypes++;
		typesClasses.put(type.getName(), new EquivalenceClass(false, type));
		return type;
	}

	public boolean registerFinalType(Type t) {
		if (typesClasses.containsKey(t.getName())) {
			return false;
		}

		typesClasses.put(t.getName(), new EquivalenceClass(true, t));
		return true;
	}

	public Type createApplicationType(Type core) {
		Type var1 = createVaryingType();
		Type var2 = createVaryingType();
		ApplicationType at = new ApplicationType(var1,var2);
		typesClasses.put(at.getName(), new EquivalenceClass(true, at));
		typesClasses.get(core.getName()).joinTo(typesClasses.get(at.getName()));
		return at;
	}

	public Type degenerate(Type t) {
		Map<Type, Type> cache = new HashMap<>();

		return degenerateInternal(cache, t);
	}

	private Type degenerateInternal(Map<Type, Type> cache, Type cur) {
		if (cur instanceof GenericType) {
			if (!cache.containsKey(cur)) {
				Type var = createVaryingType();
				cache.put(cur, var);
			}
			return cache.get(cur);
		}
		if (cur instanceof ApplicationType) {
			Type nlhs = degenerateInternal(cache, ((ApplicationType) cur).getLhs());
			Type nrhs = degenerateInternal(cache, ((ApplicationType) cur).getRhs());
			Type res = new ApplicationType(nlhs, nrhs);
			registerFinalType(res);
			return res;
		}
		if (cur instanceof ListType) {
			Type core = degenerateInternal(cache, ((ListType) cur).getCore());
			Type res = new ListType(core);
			registerFinalType(res);
			return res;
		}
		return cur;
	}

	public Type equate(String name1, String name2) {
		EquivalenceClass eq1 = typesClasses.get(name1).getRealClass();
		EquivalenceClass eq2 = typesClasses.get(name2).getRealClass();

		if (!eq1.joinTo(eq2)) {
			throw new TypeInferenceException("Cannot equate types: " + name1 + " and " + name2);
		}
		return eq2.getParent();
	}

	public Type getType(String name) {
		return typesClasses.get(name).getParent();
	}

	public Type generify(Type t) {
		return generifyInternal(t);
	}

	private Type generifyInternal(Type t) {
		if (t instanceof ListType) {
			Type tmp = generifyInternal(((ListType) t).getCore());
			Type list = new ListType(tmp);
			registerFinalType(list);
			return list;
		}
		if (t instanceof ApplicationType) {
			Type lv = generifyInternal(((ApplicationType) t).getLhs());
			Type rv = generifyInternal(((ApplicationType) t).getRhs());
			Type app = new ApplicationType(lv, rv);
			registerFinalType(app);
			return app;
		}
		if (t instanceof VaryingType) {
			Type t1 = getType(t.getName());
			if (t1 instanceof VaryingType) {
				equate(t1.getName(), createGenericType().getName());
				return getType(t1.getName());
			} else {
				return generify(t1);
			}
		}
		return t;
	}

	private static class EquivalenceClass {
		private final boolean isFinal;
		private final Type parent;
		private EquivalenceClass link;

		public EquivalenceClass(boolean isFinal, Type parent) {
			this.isFinal = isFinal;
			this.parent = parent;
			this.link = this;
		}

		public Type getParent() {
			return getRealClass().parent;
		}

		public EquivalenceClass getRealClass() {
			if (link == this) {
				return this;
			}
			return link = link.getRealClass();
		}

		public boolean joinTo(EquivalenceClass other) {
			other.getParent();
			this.getParent();
			if (isFinal) {
				return other.link == link;
			}
			this.link = other;
			return true;
		}

	}
}
