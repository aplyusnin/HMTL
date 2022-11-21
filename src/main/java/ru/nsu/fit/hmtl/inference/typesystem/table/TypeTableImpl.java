package ru.nsu.fit.hmtl.inference.typesystem.table;

import ru.nsu.fit.hmtl.inference.typesystem.types.EmptyType;
import ru.nsu.fit.hmtl.inference.typesystem.types.Type;
import ru.nsu.fit.hmtl.inference.typesystem.TypeInferenceException;
import ru.nsu.fit.hmtl.inference.typesystem.types.TypeBuilder;
import ru.nsu.fit.hmtl.misc.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	private boolean initialized = false;

	private TypeTableImpl() {
		types = new ArrayList<>();
		parentRefs = new ArrayList<>();
	}

	@Override
	public void init() {
		Preconditions.checkNotNull(EmptyType.getInstance(), "experimental");
		types.clear();
		parentRefs.clear();
		types.add(EmptyType.getInstance());
		parentRefs.add(0);
		initialized = true;
	}

	private int getRealId(int id) {
		Preconditions.checkState(initialized, "Cannot use Table without initialization");
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
		Preconditions.checkState(initialized, "Cannot use Table without initialization");
		Preconditions.checkState(0 < id && id <= types.size(), "Calling non-existing type");
		return getTypeByIDInternal(id);
	}

	private Type getTypeByIDInternal(int id) {
		int realId = getRealId(id);
		return types.get(realId);
	}

	@Override
	public int createAndRegisterType(TypeBuilder builder) {
		Preconditions.checkState(initialized, "Cannot use Table without initialization");
		int nId = types.size();
		builder.setId(nId);
		types.add(builder.build());
		parentRefs.add(nId);
		return nId;
	}

	@Override
	public void unifyTypes(int idl, int idr) throws TypeInferenceException {
		Preconditions.checkState(initialized, "Cannot use Table without initialization");
		Preconditions.checkState(0 <= idl && idl < types.size(), "Calling non-existing type");
		Preconditions.checkState(0 <= idr && idr <= types.size(), "Calling non-existing type");
		idl = getRealId(idl);
		idr = getRealId(idr);
		// already unified
		if (idl == idr) {
			return;
		}

		Type left = getTypeByIDInternal(idl);
		Type right = getTypeByIDInternal(idr);
		// left-hand unification
		Optional<Type> res;
		res = left.getUnificationStrategy().unifyWith(left, right);
		if (res.isPresent()) {
			parentRefs.set(idl, res.get().getId());
			parentRefs.set(idr, res.get().getId());
			return;
		}
		res = right.getUnificationStrategy().unifyWith(right, left);
		if (res.isPresent()) {
			parentRefs.set(idl, res.get().getId());
			parentRefs.set(idr, res.get().getId());
			return;
		}
		throw new TypeInferenceException("Cannot unify types: " + left.getName() + " and " + right.getName());
	}

	@Override
	public int getNumberOfRegisteredTypes() {
		Preconditions.checkState(initialized, "Cannot use Table without initialization");
		return types.size();
	}
}
