package ru.nsu.fit.hmtl.source.codegen.builders;

import org.burningwave.core.assembler.ComponentContainer;
import org.burningwave.core.assembler.ComponentSupplier;
import org.burningwave.core.classes.ClassFactory;
import org.burningwave.core.classes.ClassSourceGenerator;
import org.burningwave.core.classes.TypeDeclarationSourceGenerator;
import org.burningwave.core.classes.UnitSourceGenerator;
import ru.nsu.fit.hmtl.core.Applicable;
import ru.nsu.fit.hmtl.core.ExecutionContext;

import java.lang.reflect.Modifier;

/**
 * Abstract class for source builders.
 */
public abstract class Builder {

	protected UnitSourceGenerator cusg;
	protected ClassSourceGenerator ccsg;

	protected final String name;
	protected final String packageName = "ru.nsu.fit.hmtl.core.generated";
	protected final ExecutionContext context;


	public Builder(String name, ExecutionContext context) {
		this.context = context;
		this.name = name;
		setupGenerators();
	}

	/**
	 * Method for building result.
	 * @return class of generated source
	 */
	protected Class<?> make() {
		cusg.addClass(ccsg);
		System.out.println(cusg.make());

		cusg.storeToClassPath("target/classes/");

		ComponentSupplier componentSupplier = ComponentContainer.getInstance();
		ClassFactory classFactory = componentSupplier.getClassFactory();

		ClassFactory.ClassRetriever classRetriever = classFactory.loadOrBuildAndDefine(cusg);
		return classRetriever.get(packageName + "." + name);
	}

	/**
	 * Setup generators for builder.
	 */
	protected void setupGenerators() {
		cusg = UnitSourceGenerator.create(packageName);

		ccsg = ClassSourceGenerator.create(TypeDeclarationSourceGenerator.create(name));
		ccsg.addModifier(Modifier.PUBLIC);
		ccsg.addConcretizedType(Applicable.class);
	}
}
