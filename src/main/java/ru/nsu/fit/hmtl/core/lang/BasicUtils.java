package ru.nsu.fit.hmtl.core.lang;

import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

/**
 * Utils for creating of basic Objects
 */
public class BasicUtils {

	public static BasicObject createNumeric(int value) {
		return new BasicObject(value, TypeTable.getInstance().getType("BT_Numeric"));
	}

	public static BasicObject createBool(boolean value) {
		return new BasicObject(value, TypeTable.getInstance().getType("BT_Bool"));
	}

	public static BasicObject createChar(char value) {
		return new BasicObject(value, TypeTable.getInstance().getType("BT_Char"));
	}

	public static BasicObject createByte(byte value) {
		return new BasicObject(value, TypeTable.getInstance().getType("BT_Byte"));
	}

	public static Type getInt() {
		return TypeTable.getInstance().getType("BT_Numeric");
	}

	public static Type getBool() {
		return TypeTable.getInstance().getType("BT_Bool");
	}

	public static Type getChar() {
		return TypeTable.getInstance().getType("BT_Char");
	}

	public static Type getByte() {
		return TypeTable.getInstance().getType("BT_Byte");
	}

	public static Type getGeneric() {
		return TypeTable.getInstance().createGenericType();
	}
}
