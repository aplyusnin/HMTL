package ru.nsu.fit.hmtl.core.lang.io;

import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.lang.BasicObject;
import ru.nsu.fit.hmtl.core.lang.BasicUtils;
import ru.nsu.fit.hmtl.core.lang.Function;
import ru.nsu.fit.hmtl.core.lang.ListObject;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.ApplicationType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.Collections;
import java.util.List;

public class Print extends Function {

	private final Type arg;

	{
		arg = BasicUtils.getGeneric();
		type = new ApplicationType(arg, arg);
		TypeTable.getInstance().registerFinalType(type);
	}

	@Override
	public Expression eval() {
		if (applied.size() < 1) return this;

		Expression expr = applied.get(0).eval();

		if (expr instanceof BasicObject) {
			BasicObject o = (BasicObject) expr;
			System.out.println(o.getValue());
		}
		else if (expr instanceof ListObject) {
			ListObject l = (ListObject) expr;
			System.out.println("[");
			for (int i = l.getPos(); i < l.getData().size(); i++) {
				Expression p = new Print();
				p = p.apply(l.getData().get(i));
				p.eval();
				if (i + 1 < l.getData().size()) System.out.println(", ");
			}
			System.out.println("]");
		}
		else{
			System.out.println("Function " + applied.get(0).toString());
		}
		return expr;
	}

	@Override
	public List<Type> getArgTypes() {
		return List.of(arg);
	}

	@Override
	public String toString() {
		return "print";
	}
}
