package ru.nsu.fit.hmtl.source.codegen.builders;

import org.burningwave.core.classes.*;
import ru.nsu.fit.hmtl.core.*;
import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.misc.Preconditions;

import java.lang.reflect.Modifier;

public class FunctionBuilder extends Builder {

	private static int functionCount = 1;

	private FunctionSourceGenerator cfsg;

	private int vars = 0;

	public FunctionBuilder(ExecutionContext context) {
		super("Applicable" + functionCount, context);
		functionCount++;
		setupGenerators();
	}

	public Class<?> make() {
		cfsg.useType(TypedObject.class);
		ccsg.addMethod(cfsg);

		return super.make();
	}

	/**
	 * Apply applicable wrapper to another
	 * @param i - id of the first wrapper
	 * @param j - id of the second wrapper
	 * @return number of the new stored variable
	 */
	public int apply(int i, int j) {
		Preconditions.checkState(i < vars, "Wrapper with id " + i + " does not exist");
		Preconditions.checkState(j < vars, "Wrapper with id " + j + " does not exist");
		return applyInternal(getResource(i), getResource(j));
	}

	public int apply(int i, String j) {
		Preconditions.checkState(i < vars, "Wrapper with id " + i + " does not exist");
		Preconditions.checkState(context.contains(j), "Wrapper with id " + j + " does not exist");
		return applyInternal(getResource(i), getResource(j));
	}

	public int apply(String i, int j) {
		Preconditions.checkState(j < vars, "Wrapper with id " + i + " does not exist");
		Preconditions.checkState(context.contains(i), "Wrapper with id " + j + " does not exist");
		return applyInternal(getResource(i), getResource(j));
	}


	public int apply(String i, String j) {
		Preconditions.checkState(context.contains(i), "Wrapper with id " + i + " does not exist");
		Preconditions.checkState(context.contains(j), "Wrapper with id " + j + " does not exist");
		return applyInternal(getResource(i), getResource(j));
	}

	private int applyInternal(String res1, String res2) {
		cfsg.addBodyCodeLine("Expression V_" + vars + " = " + res1 + ".apply(" + res2 + ");");
		vars++;
		return vars - 1;
	}

	private String getResource(int i) {
		return "V_" + i;
	}

	private String getResource(String name) {
		return "(ctx.lookup(\"" + name + "\"))";
	}

	/**
	 * Evaluate value of applicableWrapper with id i. Stores result into new variable.
	 * @param i - id of wrapper
	 * @return id of result
	 */
	public int eval(int i) {
		Preconditions.checkState(i < vars, "Wrapper with id " + i + " does not exist");
		cfsg.addBodyCode("vars.add(ApplicableWrapper.fromTypedObject(vars.get(" + i + ").apply()));\n");
		return vars++;
	}

	public void callReturn(int i) {
		Preconditions.checkState(i < vars, "");
		callReturnInternal(getResource(i));
	}

	public void callReturn(String i) {
		Preconditions.checkState(context.contains(i), "");
		callReturnInternal(getResource(i));
	}

	private void callReturnInternal(String res) {
		cfsg.addBodyCodeLine("return " + res + ".eval();");
	}

	@Override
	protected void setupGenerators() {
		super.setupGenerators();
		cusg.addImport(Expression.class);

		cfsg = FunctionSourceGenerator.create("eval");
		cfsg.addAnnotation(AnnotationSourceGenerator.create(Override.class));
		cfsg.addParameter(VariableSourceGenerator.create(ExecutionContext.class, "ctx"));
		cfsg.addModifier(Modifier.PUBLIC);
		cfsg.setReturnType(Expression.class);
	}
}
