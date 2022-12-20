package ru.nsu.fit.hmtl.core.stl.functions.descriptors;

import ru.nsu.fit.hmtl.core.Applicable;
import ru.nsu.fit.hmtl.core.stl.StlFunctionDescriptor;
import ru.nsu.fit.hmtl.core.stl.functions.ApplicablePrint;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.List;

public class PrintDescription implements StlFunctionDescriptor {

	private final Applicable func = new ApplicablePrint();

	private final Type temp1 = TypeTable.getInstance().createGenericType();

	@Override
	public Applicable getFunction() {
		return func;
	}

	@Override
	public String getName() {
		return "print";
	}

	@Override
	public List<String> getArgs() {
		return List.of("a");
	}

	@Override
	public List<Type> getTypes() {
		return List.of(temp1);
	}

	@Override
	public Type getFunctionType() {
		return temp1;
	}

}
