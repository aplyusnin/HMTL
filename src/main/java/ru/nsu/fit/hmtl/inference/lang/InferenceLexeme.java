package ru.nsu.fit.hmtl.inference.lang;

import ru.nsu.fit.hmtl.inference.context.Context;
import ru.nsu.fit.hmtl.inference.typesystem.TypeInformation;
import ru.nsu.fit.hmtl.inference.typesystem.types.Type;
import ru.nsu.fit.hmtl.inference.typesystem.TypeInferenceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class for representing inference of lexeme type.
 *
 * InferenceLexeme schema for some cases cases:
 *
 * (defn f [a b] (a b)) ---> (bind f (context (bind a t1) (bind b t2) (apply a b)))
 *
 * (def x f) --->  (bind x f)
 *
 * (def x (fn [a b c] ((a b) c))) ---> (bind x (context (bind a t1) (bind b t2) (bind c t3) (apply (apply a b) c)))
 */
public abstract class InferenceLexeme {

	protected final Optional<String> name;
	protected final List<InferenceLexeme> subLexemes;
	protected final TypeInformation information;

	public InferenceLexeme(String name) {
		this.name = Optional.ofNullable(name);
		subLexemes = new ArrayList<>();
		information = new TypeInformation();
	}

	public void addLexeme(InferenceLexeme lexeme) {
		subLexemes.add(lexeme);
	}

	public List<InferenceLexeme> getSubLexemes() {
		return subLexemes;
	}

	public Optional<String> getName() {
		return name;
	}

	public TypeInformation getInformation() {
		return information;
	}

	/**
	 * Process sublexemes and infer type of the given
	 * @param context for lexeme
	 * @return index of type of processed lexeme
	 */
	public abstract int process(Context context) throws TypeInferenceException;

}
