package ru.nsu.fit.hmtl.core.lang.io;

import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.lang.BasicUtils;
import ru.nsu.fit.hmtl.core.lang.Function;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ReadByte extends Function {

	private static final Scanner sc = IOScanner.sc;
	{
		type = BasicUtils.getByte();
	}

	@Override
	public Expression evalInternal(ExecutionContext ctx) {
		byte val = sc.nextByte();
		return BasicUtils.createByte(val);
	}

	@Override
	public List<Type> getArgTypes() {
		return Collections.emptyList();
	}

	@Override
	public String toString() {
		return "readByte";
	}
}