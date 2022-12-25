package ru.nsu.fit.hmtl.core.lang.list;

import ru.nsu.fit.hmtl.core.lang.BasicObject;
import ru.nsu.fit.hmtl.core.lang.BasicUtils;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.ApplicationType;
import ru.nsu.fit.hmtl.core.typesystem.types.ListType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;
import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.lang.Function;
import ru.nsu.fit.hmtl.core.lang.ListObject;

import java.util.Collections;
import java.util.List;

public class MakeList extends Function {

	{
		Type arg = BasicUtils.getGeneric();
		type = new ListType(arg);
		TypeTable.getInstance().registerFinalType(type);
	}

	@Override
	public Expression eval() {
		var res = new ListObject(type);
		res.getData().add(applied.get(0).eval());
		return res;
	}

	@Override
	public List<Type> getArgTypes() {
		return Collections.emptyList();
	}

	@Override
	public String toString() {
		return "list";
	}
}
