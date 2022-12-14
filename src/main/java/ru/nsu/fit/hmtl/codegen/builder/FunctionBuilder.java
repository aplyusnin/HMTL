package ru.nsu.fit.hmtl.codegen.builder;

import org.burningwave.core.assembler.ComponentContainer;
import org.burningwave.core.assembler.ComponentSupplier;
import org.burningwave.core.classes.*;
import ru.nsu.fit.hmtl.core.Applicable;
import ru.nsu.fit.hmtl.core.TypedObject;
import ru.nsu.fit.hmtl.core.WrapperList;
import ru.nsu.fit.hmtl.misc.Preconditions;

import java.lang.reflect.Modifier;

public class FunctionBuilder {

	private static int functionCount = 0;

	private UnitSourceGenerator cusg;
	private ClassSourceGenerator ccsg;
	private FunctionSourceGenerator cfsg;
	private FunctionSourceGenerator argsInfoGenerator;

	private int vars = 2;

	private final String name;
	private final String packageName = "ru.nsu.fit.hmtl.core.generated";


	public FunctionBuilder() {
		functionCount++;
		name = "Applicable" + functionCount;
		setupGenerators();
	}

	public Class<?> make()
	{
		cfsg.useType(TypedObject.class);
		ccsg.addMethod(cfsg);
		cusg.addClass(ccsg);
		System.out.println(cusg.make());

		cusg.storeToClassPath("target/classes/ru/nsu/fit/hmtl/core/generated");

		ComponentSupplier componentSupplier = ComponentContainer.getInstance();
		ClassFactory classFactory = componentSupplier.getClassFactory();

		ClassFactory.ClassRetriever classRetriever = classFactory.loadOrBuildAndDefine(cusg);
		return classRetriever.get(packageName + "." + name);
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
		cfsg.addBodyCode("vars.add(vars.get(" + i + ").apply(vars.get(" + j + ")));\n");
		return vars++;
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
		cfsg.addBodyCode("return vars.get(" + i + ").eval();\n");
	}

	private void setupGenerators() {
		cusg = UnitSourceGenerator.create(packageName);

		ccsg = ClassSourceGenerator.create(TypeDeclarationSourceGenerator.create("Applicable" + functionCount));
		ccsg.addModifier(Modifier.PUBLIC);
		ccsg.addConcretizedType(Applicable.class);

		cfsg = FunctionSourceGenerator.create("apply");
		cfsg.addAnnotation(AnnotationSourceGenerator.create(Override.class));
		cfsg.addParameter(VariableSourceGenerator.create(WrapperList.class, "vars"));
		cfsg.addModifier(Modifier.PUBLIC);
		cfsg.setReturnType(TypedObject.class);
	}
}
