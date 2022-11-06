package ru.nsu.fit.hmtl.inference;

import ru.nsu.fit.hmtl.misc.Preconditions;

import java.util.Arrays;
import java.util.List;

/** Class represents functional type during type inference. */
public class FunctionalType {

	private ConstantType result;
	private final List<ConstantType> args;

	public FunctionalType(ConstantType result, ConstantType... args) {
		Preconditions.checkState(args.length > 0, "Function should have at least 1 argument");
		this.result = result;
		this.args = Arrays.asList(args);
	}

	//
	// Getters ans setters
	//

	public ConstantType getResult() {
		return result;
	}

	public List<ConstantType> getArgs() {
		return args;
	}

	public void updateArgumentType(int id, ConstantType newType) {
		Preconditions.checkState(id < args.size(), "Argument doesn't exists");
		this.args.set(id, newType);
	}

	public void updateResultType(ConstantType newType) {
		this.result = newType;
	}
}
