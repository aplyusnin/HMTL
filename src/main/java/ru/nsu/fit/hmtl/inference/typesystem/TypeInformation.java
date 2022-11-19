package ru.nsu.fit.hmtl.inference.typesystem;

import ru.nsu.fit.hmtl.inference.context.Context;

/**
 * Class for storing information about type and context of this type.
 */
public class TypeInformation {
	// id of assigned type
	private int typeId;
	private Context context;

	public TypeInformation() {
	}

	public void setTypeId(int id) {
		this.typeId = id;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public int getTypeId() {
		return typeId;
	}

}
