package ru.nsu.fit.hmtl.core.lang.common;

import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.lang.BasicObject;
import ru.nsu.fit.hmtl.core.lang.BasicUtils;
import ru.nsu.fit.hmtl.core.lang.Function;
import ru.nsu.fit.hmtl.core.lang.ListObject;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.ApplicationType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.List;
import java.util.Objects;

public class Equal extends Function {
	private final Type arg;
	{
		arg = BasicUtils.getGeneric();
		Type t1 = new ApplicationType(arg, BasicUtils.getBool());
		TypeTable.getInstance().registerFinalType(t1);
		type = new ApplicationType(arg, t1);
		TypeTable.getInstance().registerFinalType(type);
	}

	@Override
	public Expression eval() {
		if (applied.size() < 2) return this;
		Expression llo = applied.get(0).eval();
		Expression rlo = applied.get(0).eval();

		if (llo.getClass() != rlo.getClass()) {
			return BasicUtils.createBool(false);
		}
		if (llo.getClass() == BasicObject.class) {
			BasicObject lhs = (BasicObject) applied.get(0).eval();
			BasicObject rhs = (BasicObject) applied.get(1).eval();
			return BasicUtils.createBool(Objects.equals(lhs.getValue(), rhs.getValue()));
		}
		if (llo.getClass() == ListObject.class) {
			ListObject lhs = (ListObject) applied.get(0).eval();
			ListObject rhs = (ListObject) applied.get(1).eval();

			if (lhs.getData().size() - lhs.getPos() != rhs.getData().size() - rhs.getPos()) {
				return BasicUtils.createBool(false);
			}
			for (int i = lhs.getPos(); i < lhs.getData().size(); i++) {
				Expression eq = new Equal();
				eq = eq.apply(lhs.getData().get(i));
				eq = eq.apply(rhs.getData().get(i));
				BasicObject res = (BasicObject) eq.eval();
				if (!((Boolean) res.getValue())) {
					return BasicUtils.createBool(false);
				}
			}
			return BasicUtils.createBool(true);
		}

		return BasicUtils.createBool(false);
	}

	@Override
	public List<Type> getArgTypes() {
		return List.of(arg, arg);
	}

	@Override
	public String toString() {
		return "=";
	}

}