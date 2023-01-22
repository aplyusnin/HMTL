package ru.nsu.fit.hmtl.core.lang.list;

import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.lang.Function;
import ru.nsu.fit.hmtl.core.lang.ListObject;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.ApplicationType;
import ru.nsu.fit.hmtl.core.typesystem.types.ListType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.util.List;

public class Concat extends Function {

	private Type arg;

	{
		arg = TypeTable.getInstance().createGenericType();
		arg = new ListType(arg);
		TypeTable.getInstance().registerFinalType(arg);
		type = new ApplicationType(arg, arg);
		TypeTable.getInstance().registerFinalType(type);
		type = new ApplicationType(arg, type);
		TypeTable.getInstance().registerFinalType(type);
	}

	@Override
	public Expression evalInternal(ExecutionContext ctx) {
		ListObject ll = (ListObject) applied.get(0).eval(ctx);
		ListObject rl = (ListObject) applied.get(1).eval(ctx);

		ListObject lo = new ListObject(ll.getType());
		for (int i = ll.getPos(); i < ll.getData().size(); i++) {
			lo.getData().add(ll.getData().get(i));
		}
		for (int i = rl.getPos(); i < rl.getData().size(); i++) {
			lo.getData().add(rl.getData().get(i));
		}
		return lo;
	}

	@Override
	public List<Type> getArgTypes() {
		return List.of(arg, arg);
	}

	@Override
	public String toString() {
		return "con";
	}
}
