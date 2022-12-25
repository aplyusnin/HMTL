package ru.nsu.fit.hmtl.core.lang.io;

import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.lang.BasicUtils;
import ru.nsu.fit.hmtl.core.lang.Function;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ReadNum extends Function {

	private static final Scanner sc = IOScanner.sc;
	{
		type = BasicUtils.getInt();
	}

	@Override
	public Expression eval() {
		int val = sc.nextInt();
		return BasicUtils.createNumeric(val);
	}

	@Override
	public List<Type> getArgTypes() {
		return Collections.emptyList();
	}

	@Override
	public String toString() {
		return "readNum";
	}
}
