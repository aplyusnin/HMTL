package ru.nsu.fit.hmtl.core.lang;

import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.StlExecutionContext;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;
import ru.nsu.fit.hmtl.core.Expression;

import java.util.ArrayList;
import java.util.List;

public class ListObject implements Expression {

	private final List<Expression> list = new ArrayList<>();
	private int pos = 0;
	private final Type type;
	public ExecutionContext ctx = StlExecutionContext.getInstance();

	public ListObject(Type type) {
		this.type = type;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Expression deepCopy() {
		ListObject list1 = new ListObject(type);

		for (int i = pos; i < list.size(); i++) {
			list1.list.add(list.get(i).deepCopy());
		}

		list1.ctx = ctx;

		return list1;
	}

	public void inc() {
		pos++;
	}

	public int getPos() {
		return pos;
	}

	public List<Expression> getData() {
		return list;
	}

}
