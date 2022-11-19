package ru.nsu.fit.hmtl.inference.lang;

import ru.nsu.fit.hmtl.inference.context.Context;
import ru.nsu.fit.hmtl.inference.typesystem.types.Type;
import ru.nsu.fit.hmtl.inference.typesystem.TypeInferenceException;
import ru.nsu.fit.hmtl.misc.Preconditions;


public class ConstantLexeme extends InferenceLexeme {

	public ConstantLexeme(String name) {
		super(name);
	}

	@Override
	public int process(Context context) throws TypeInferenceException {
		Preconditions.checkState(name.isPresent(), "Name of constant lexeme should exist");

		int typeId = context.getTypeId(name.get());
		// binding type to the lexeme
		getInformation().setContext(context);
		getInformation().setTypeId(typeId);

		return typeId;
	}

}
