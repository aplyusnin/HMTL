package ru.nsu.fit.hmtl.core.typesystem.context;

import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

public class ParsingTypeContext implements TypeContext {

	@Override
	public Type lookup(String name) {
		if (isBool(name)) return TypeTable.getInstance().getType("BT_Bool");
		if (isChar(name)) return TypeTable.getInstance().getType("BT_Char");
		if (isByte(name)) return TypeTable.getInstance().getType("BT_Byte");
		if (isNum(name))  return TypeTable.getInstance().getType("BT_Numeric");
		throw new RuntimeException("Unknown lex: " + name);
	}

	@Override
	public TypeContext createSubContext() {
		return null;
	}

	@Override
	public void setType(String name, Type type) {
	}

	private boolean isBool(String val) {
		return "true".equalsIgnoreCase(val) || "false".equalsIgnoreCase(val);
	}

	private boolean isChar(String val) {
		return val.length() == 3 && val.charAt(0) == val.charAt(2) && val.charAt(0) == '\'';
	}

	private boolean isByte(String val) {
		return val.length() == 4 && val.charAt(0) == '0' && val.charAt(1) == 'x' && isHex(val.charAt(2)) && isHex(val.charAt(3));
	}

	private boolean isHex(char c) {
		return Character.isDigit(c) || ('a' <= Character.toLowerCase(c) && Character.toLowerCase(c) <= 'f');
	}

	private boolean isNum(String val) {
		try {
			Integer.parseInt(val);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
