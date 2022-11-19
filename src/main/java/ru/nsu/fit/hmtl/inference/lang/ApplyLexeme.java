package ru.nsu.fit.hmtl.inference.lang;

import ru.nsu.fit.hmtl.inference.context.Context;
import ru.nsu.fit.hmtl.inference.typesystem.TypeInferenceException;
import ru.nsu.fit.hmtl.inference.typesystem.TypeUtils;
import ru.nsu.fit.hmtl.inference.typesystem.table.TypeTableImpl;
import ru.nsu.fit.hmtl.inference.typesystem.types.Type;
import ru.nsu.fit.hmtl.misc.Preconditions;

/**
 * Class represent of lexeme application.
 */
public class ApplyLexeme extends InferenceLexeme {

	public ApplyLexeme(String name) {
		super(name);
	}

	@Override
	public int process(Context context) throws TypeInferenceException {
		Preconditions.checkState(
				subLexemes.size() > 1,
				"Application should have at least function name and one argument");

		int resultId = subLexemes.get(0).process(context);


		for (int i = 1; i < subLexemes.size(); i++) {
			int argId = subLexemes.get(i).process(context);

			Type ft = TypeTableImpl.getInstance().getTypeByID(resultId);
			Type at = TypeTableImpl.getInstance().getTypeByID(argId);

			resultId = TypeUtils.application(ft, at);
		}

		getInformation().setContext(context);
		getInformation().setTypeId(resultId);

		return resultId;
	}

}
