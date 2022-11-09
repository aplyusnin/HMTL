package ru.nsu.fit.hmtl.inference;

/** Class representing node of type system. */
public class InferenceType {
	// Unique id used in type inference
	private int id;

	// Nullable name, if exists, tied type is final and cannot be changed
	private String name;

	public InferenceType() {
		this.id = -1;
		name = null;
	}

	public InferenceType(int id) {
		this.id = id;
		name = null;
	}

	public InferenceType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public boolean isFinal() {
		return name != null;
	}

	public boolean isComplex() {
		return false;
	}

	public String getPrettyName() {
		if (isComplex()) return "(" + getName() + ")";
		return getName();
	}

	public String getName() {
		return isFinal() ? name : "T_" + id;
	}

	public int getId() {
		return id;
	}

}
