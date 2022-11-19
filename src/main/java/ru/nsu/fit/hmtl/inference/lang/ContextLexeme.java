package ru.nsu.fit.hmtl.inference.lang;

import ru.nsu.fit.hmtl.inference.context.Context;
import ru.nsu.fit.hmtl.inference.context.ContextUtils;
import ru.nsu.fit.hmtl.inference.typesystem.types.Type;
import ru.nsu.fit.hmtl.inference.typesystem.TypeInferenceException;
import ru.nsu.fit.hmtl.misc.Preconditions;

/**
 * Lexeme that instantiate new sub-context to work with.
 */
public class ContextLexeme extends InferenceLexeme {

	public ContextLexeme(String name) {
		super(name);
	}

	@Override
	public int process(Context context) throws TypeInferenceException {
		Preconditions.checkState(subLexemes.size() > 0, "Context should impact on at least 1 lexeme");
		Context subContext = ContextUtils.createSubContext(context);

		// By default, this context is empty
		int typeId = 0;

		for (InferenceLexeme lex : subLexemes) {
			typeId = lex.process(subContext);
		}

		return typeId;
	}

}
