package ru.nsu.fit.hmtl.inference.context;

import ru.nsu.fit.hmtl.inference.typesystem.table.TypeTableImpl;
import ru.nsu.fit.hmtl.inference.typesystem.types.Type;
import ru.nsu.fit.hmtl.inference.typesystem.TypeUtils;

import java.util.HashMap;
import java.util.Map;

public class DefaultContext implements Context {

	// parent context is used for lookup if no info found in current
	private final Context parentContext;

	private final Map<String, Integer> namedTypes;

	public DefaultContext(Context parentContext) {
		this.parentContext = parentContext;
		this.namedTypes = new HashMap<>();
	}

	@Override
	public int getTypeId(String name) throws ContextException {
		if (namedTypes.containsKey(name)) return namedTypes.get(name);
		return parentContext.getTypeId(name);
	}

	@Override
	public void bindTypeId(String name, int typeId, boolean allowRewrite) throws ContextException {
		if (!namedTypes.containsKey(name)) {
			namedTypes.put(name, typeId);
		}
		else if (allowRewrite) {
			namedTypes.put(name, typeId);
		}
		else {
			throw new ContextException("Trying to rewrite existing variable");
		}
	}

	@Override
	public void updateType(String name, int typeId) throws ContextException {
		if (namedTypes.containsKey(name)) {
			Type prev = TypeTableImpl.getInstance().getTypeByID(namedTypes.get(name));
			Type cur = TypeTableImpl.getInstance().getTypeByID(typeId);
			if (TypeUtils.substitutable(prev, cur)) {
				namedTypes.put(name, typeId);
			} else {
				throw new ContextException("Cannot substitute type: " + prev.getName() + " with " + cur.getName());
			}
		} else {
			parentContext.updateType(name, typeId);
		}
	}

}
