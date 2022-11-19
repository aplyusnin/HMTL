package ru.nsu.fit.hmtl.inference.typesystem.table;

import ru.nsu.fit.hmtl.inference.typesystem.types.Type;
import ru.nsu.fit.hmtl.inference.typesystem.TypeInferenceException;
import ru.nsu.fit.hmtl.inference.typesystem.types.TypeBuilder;
import ru.nsu.fit.hmtl.misc.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of {@link TypeTable}.
 */
public class TypeTableImpl implements TypeTable {

	// Make this implementation singleton to have access everywhere
	private final static TypeTableImpl instance = new TypeTableImpl();

	public static TypeTableImpl getInstance() {
		return instance;
	}

	// list with registered types
	private final List<Type> types;

	// list with link to the actual types registered by id
	private final List<Integer> parentRefs;


	private TypeTableImpl() {
		types = new ArrayList<>();
		parentRefs = new ArrayList<>();
	}

	private int getRealId(int id) {
		// if type doesn't link to any other types than it's typed isn't unified with any
		if (parentRefs.get(id) == id) {
			return id;
		}
		// calling real id from type which is unified with current
		int realID = getRealId(parentRefs.get(id));

		/*
		  Update link ot unified id.
		  if type A is unified with type B and type B is unified with type C
		  than type A is also unified with type C, so it should link to id of C
		 */
		parentRefs.set(id, realID);
		return realID;
	}

	@Override
	public Type getTypeByID(int id) {
		Preconditions.checkState(id < types.size(), "Calling non-existing type");
		int realId = getRealId(id);
		return types.get(realId);
	}

	@Override
	public int createAndRegisterType(TypeBuilder builder) {
		int nId = types.size();
		builder.setId(nId);
		types.add(builder.build());
		parentRefs.add(nId);
		return nId;
	}

	@Override
	public void unifyTypes(int idl, int idr) throws TypeInferenceException {
		Type left = getTypeByID(idl);
		Type right = getTypeByID(idr);
		// left-hand unification
		{

		}
	}

}
