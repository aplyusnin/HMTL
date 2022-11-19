package ru.nsu.fit.hmtl.inference.lang;

import ru.nsu.fit.hmtl.inference.context.Context;
import ru.nsu.fit.hmtl.inference.typesystem.TypeInferenceException;
import ru.nsu.fit.hmtl.inference.typesystem.TypeUtils;
import ru.nsu.fit.hmtl.inference.typesystem.table.TypeTableImpl;
import ru.nsu.fit.hmtl.misc.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for representing function definition.
 *
 * (fn [a b c] (g ...)) ---> (func (bind a t1) (bind b t2) (bind c t3) (apply g ))
 */
public class FunctionalLexeme extends InferenceLexeme {

	public FunctionalLexeme(String name) {
		super(name);
	}

	@Override
	public int process(Context context) throws TypeInferenceException {
		Preconditions.checkState(subLexemes.size() > 0, "There should be at least function body");
		List<Integer> typeIds = new ArrayList<>();
		for (int i = 0; i + 1 < subLexemes.size(); i++) {
			typeIds.add(subLexemes.get(i).process(context));
		}
		int resId = subLexemes.get(subLexemes.size() - 1).process(context);
		for (int i = typeIds.size() - 1; i >= 0; i--) {
			resId = TypeUtils.abstraction(TypeTableImpl.getInstance().getTypeByID(typeIds.get(i)), TypeTableImpl.getInstance().getTypeByID(resId));
		}

		getInformation().setTypeId(resId);
		getInformation().setContext(context);

		context.bindTypeId(getName().get(), resId, true);

		return resId;
	}

}
