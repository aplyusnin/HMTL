package ru.nsu.fit.hmtl.inference.lang;

import ru.nsu.fit.hmtl.inference.context.Context;
import ru.nsu.fit.hmtl.inference.typesystem.table.TypeTableImpl;
import ru.nsu.fit.hmtl.inference.typesystem.types.DefaultTypeBuilder;
import ru.nsu.fit.hmtl.inference.typesystem.types.Type;
import ru.nsu.fit.hmtl.inference.typesystem.TypeInferenceException;
import ru.nsu.fit.hmtl.misc.Preconditions;

/**
 * Lexeme used for binding variable and types to context.
 */
public class BindLexeme extends InferenceLexeme {

	public BindLexeme(String name) {
		super(name);
	}

	@Override
	public int process(Context context) throws TypeInferenceException {
		Preconditions.checkState(
				subLexemes.size() >= 1,
				"Bind inference lexeme should contains at least one lexeme for name and may contain lexeme for type");
		Preconditions.checkState(subLexemes.get(0).getName().isPresent(), "Cannot bind type to non-existing name");
		String bindName = subLexemes.get(0).getName().get();
		int typeId;
		if (subLexemes.size() > 1) {
			typeId = subLexemes.get(1).process(context);
		} else {
			typeId = TypeTableImpl.getInstance().createAndRegisterType(new DefaultTypeBuilder());
		}
		context.bindTypeId(bindName, typeId, true);

		getInformation().setTypeId(typeId);
		getInformation().setContext(context);
		return typeId;
	}

}
