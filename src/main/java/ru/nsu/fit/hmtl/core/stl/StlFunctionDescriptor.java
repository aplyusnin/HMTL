package ru.nsu.fit.hmtl.core.stl;

import ru.nsu.fit.hmtl.core.Applicable;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.List;

public interface StlFunctionDescriptor {

	Applicable getFunction();

	String getName();

	List<String> getArgs();

	List<Type> getTypes();

	Type getFunctionType();
}
