package ru.nsu.fit.hmtl.source.codegen;

import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.source.codegen.builders.FunctionBuilder;

/**
 * Interface for generating source.
 */
public interface SGNode {

	void generateSource(FunctionBuilder fb, ExecutionContext ctx);
}
