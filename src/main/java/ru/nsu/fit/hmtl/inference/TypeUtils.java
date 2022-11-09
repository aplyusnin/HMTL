package ru.nsu.fit.hmtl.inference;

public class TypeUtils {

	/** Check if two types are equal. */
	public static boolean areEqual(InferenceType t1, InferenceType t2) {
		// if types are not the same tha
		if (t1.isComplex() != t2.isComplex()) return false;
		// if both are complex
		if (t1.isComplex()) {
			return areEqual(((ApplicationType) t1).getLeftSide(), ((ApplicationType) t2).getLeftSide()) &&
					areEqual(((ApplicationType) t1).getRightSide(), ((ApplicationType) t2).getRightSide());
		}
		// if there is at least one non-final type
		if (!t1.isFinal() || !t2.isFinal()) {
			return true;
		}
		// if both types are final they both should be the same
		return t1.getId() == t2.getId();
	}
}
