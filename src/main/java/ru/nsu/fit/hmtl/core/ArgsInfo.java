package ru.nsu.fit.hmtl.core;

import ru.nsu.fit.hmtl.inference.typesystem.types.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing information about arguments of function;
 */
public class ArgsInfo {
	private final List<Type> typesOfArgs;

	private ArgsInfo(List<Type> args) {
		typesOfArgs = args;
	}

	public void validate(List<ApplicableWrapper> passedArgs) {
		if (passedArgs.size() != typesOfArgs.size()) {
			throw new RuntimeException("Arguments number mismatch");
		}
		for (int i = 0; i < typesOfArgs.size(); i++) {
			if (!typesOfArgs.get(i).equals(passedArgs.get(i).getType())) {
				throw new RuntimeException("Type mismatch: type " + passedArgs.get(i).getType().getName() + " is incompatible with " + typesOfArgs.get(i).getName());
			}
			for (int j = 0; j < i; j++) {
				if (typesOfArgs.get(i).equals(typesOfArgs.get(j))) {
					if (!passedArgs.get(i).equals(passedArgs.get(j))) {
						throw new RuntimeException("Type mismatch: type " + passedArgs.get(i).getType().getName() + " is incompatible with " + passedArgs.get(i).getType().getName());
					}
				}
			}
		}
	}

	public static class Builder {
		private List<Type> args = new ArrayList<>();

		public Builder addType(Type argType) {
			this.args.add(argType);
			return this;
		}

		public ArgsInfo build() {
			return new ArgsInfo(args);
		}
	}
}
