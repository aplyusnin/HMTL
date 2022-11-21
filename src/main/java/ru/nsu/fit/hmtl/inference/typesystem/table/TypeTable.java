package ru.nsu.fit.hmtl.inference.typesystem.table;

import ru.nsu.fit.hmtl.inference.typesystem.types.Type;
import ru.nsu.fit.hmtl.inference.typesystem.TypeInferenceException;
import ru.nsu.fit.hmtl.inference.typesystem.types.TypeBuilder;

/**
 * Type table is used for storing information about existing type.
 */
public interface TypeTable {

	/**
	 * Initialize table for further usage. Calling again would reset table to the initial state.
	 */
	void init();

	/**
	 * Requests type by index. It may not be the same, if it was unified with other type.
	 * @param id of type
	 * @return Type which is unified with the given index.
	 */
	Type getTypeByID(int id);

	/**
	 * Creates type and registers it.
	 * @param builder of the type
	 * @return - id of added type
	 */
	int createAndRegisterType(TypeBuilder builder);

	/**
	 * Unifies types by either calling for unify strategy of left type or right type.
	 * @param idl of lh type
	 * @param idr of rh type
	 * @throws TypeInferenceException if types are not unifiable
	 */
	void unifyTypes(int idl, int idr) throws TypeInferenceException;

	/**
	 * Get number of types that was registered
	 * @return number of types
	 */
	int getNumberOfRegisteredTypes();
}
