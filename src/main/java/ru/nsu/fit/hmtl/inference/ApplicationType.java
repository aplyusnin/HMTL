package ru.nsu.fit.hmtl.inference;

/** Class representing function application of types. */
public class ApplicationType extends InferenceType {
	private InferenceType leftSide, rightSide;

	public ApplicationType(InferenceType leftSide, InferenceType rightSide) {
		super();
		this.leftSide = leftSide;
		this.rightSide = rightSide;
	}

	public InferenceType getLeftSide() {
		return leftSide;
	}

	public InferenceType getRightSide() {
		return rightSide;
	}

	@Override
	public boolean isComplex() {
		return true;
	}

	@Override
	public String getName() {
		return leftSide.getPrettyName() + "->" + rightSide.getPrettyName();
	}

}
