package ru.nsu.fit.hmtl.core;

import ru.nsu.fit.hmtl.core.lang.BasicUtils;

public class ParsingExecutionContext implements ExecutionContext {

	@Override
	public Expression lookup(String name) {
		Expression value;
		value = isBool(name);
		if (value != null) return value;
		value = isChar(name);
		if (value != null) return value;
		value = isByte(name);
		if (value != null) return value;
		value = isNum(name);
		if (value != null)  return value;
		throw new RuntimeException("Unknown lex: " + name);
	}

	@Override
	public boolean contains(String name) {
		var x = lookup(name);
		return x != null;
	}

	@Override
	public ExecutionContext createSubContext() {
		return null;
	}

	@Override
	public ExecutionContext createCopy() {
		return this;
	}

	@Override
	public void setValue(String name, Expression newValue) {

	}

	@Override
	public ExecutionContext getParent() {
		return null;
	}

	private Expression isBool(String val) {
		if ("true".equalsIgnoreCase(val)) {
			return BasicUtils.createBool(true);
		}
		if ("false".equalsIgnoreCase(val)) {
			return BasicUtils.createBool(false);
		}
		return null;
	}

	private Expression isChar(String val) {
		if (val.length() == 3 && val.charAt(0) == val.charAt(2) && val.charAt(0) == '\'') {
			return BasicUtils.createChar(val.charAt(1));
		}

		return null;
	}

	private Expression isByte(String val) {
		if (val.length() == 4 && val.charAt(0) == '0' && val.charAt(1) == 'x') {
			byte ih1 = hex(val.charAt(2));
			byte ih2 = hex(val.charAt(3));
			if (ih1 != 100 && ih2 != 100) {
				var x = ih1 << 4;
				return BasicUtils.createByte((byte)(ih1 << 0x04 | ih2));
			}
		}

		return null;
	}

	private byte hex(char c) {
		if (Character.isDigit(c)) {
			return (byte)(c - '0');
		}

		char c1 = Character.toLowerCase(c);

		if ('a' <= c1 && c1 <= 'f') {
			return (byte)(c - 'a' + 10);
		}
		return 100;
	}

	private Expression isNum(String val) {
		try {
			return BasicUtils.createNumeric(Integer.parseInt(val));
		} catch (Exception e) {
			return null;
		}
	}

}
