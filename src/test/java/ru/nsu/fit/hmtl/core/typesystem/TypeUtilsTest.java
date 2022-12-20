package ru.nsu.fit.hmtl.core.typesystem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.*;


public class TypeUtilsTest {


	@Test
	public void testApplicationFail() {
		Type base = TypeTable.getInstance().createBasicType("A");
		Type var = TypeTable.getInstance().createVaryingType();
		Type list = new ListType(var);
		Type generic = TypeTable.getInstance().createGenericType();

		Assertions.assertThrows(RuntimeException.class, () -> TypeUtils.apply(base, var));
		Assertions.assertThrows(RuntimeException.class, () -> TypeUtils.apply(list, var));
		Assertions.assertThrows(RuntimeException.class, () -> TypeUtils.apply(generic, var));
	}
}
