package ru.nsu.fit.hmtl.core.typesystem;

import org.junit.jupiter.api.Test;
import ru.nsu.fit.hmtl.core.lang.BasicUtils;
import ru.nsu.fit.hmtl.core.typesystem.table.TypeTable;
import ru.nsu.fit.hmtl.core.typesystem.types.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TypeUtilsTest {

	@Test
	public void testUnifyVariables() {
		Type vt1 = TypeTable.getInstance().createVaryingType();
		Type vt2 = TypeTable.getInstance().createVaryingType();

		TypeUtils.unify(vt1, vt2);

		assertEquals(TypeTable.getInstance().getType(vt1.getName()), TypeTable.getInstance().getType(vt2.getName()));
	}

	@Test
	public void testUnifyVariablesAndBasicType() {
		Type vt1 = TypeTable.getInstance().getType("BT_Bool");
		Type vt2 = TypeTable.getInstance().createVaryingType();

		TypeUtils.unify(vt1, vt2);

		assertEquals(TypeTable.getInstance().getType(vt1.getName()), TypeTable.getInstance().getType(vt2.getName()));
	}

	@Test
	public void testUnifyBasicTypes() {
		Type vt1 = TypeTable.getInstance().getType("BT_Bool");
		Type vt2 = TypeTable.getInstance().getType("BT_Numeric");

		assertThrows(TypeInferenceException.class, () -> TypeUtils.unify(vt1, vt2));
	}

	@Test
	public void testUnifyIncompatibleApplications() {
		Type vt1 = TypeTable.getInstance().createVaryingType();
		Type vt2 = TypeTable.getInstance().createVaryingType();

		Type bt1 = TypeTable.getInstance().getType("BT_Bool");
		Type bt2 = TypeTable.getInstance().getType("BT_Numeric");

		Type ap1 = new ApplicationType(vt1, bt1);
		Type ap2 = new ApplicationType(vt2, bt2);

		TypeTable.getInstance().registerFinalType(ap1);
		TypeTable.getInstance().registerFinalType(ap2);

		assertThrows(TypeInferenceException.class, () -> TypeUtils.unify(ap1, ap2));
	}

	@Test
	public void testUnifyCompatibleApplications() {
		Type vt1 = TypeTable.getInstance().createVaryingType();
		Type vt2 = TypeTable.getInstance().createVaryingType();

		Type bt1 = TypeTable.getInstance().getType("BT_Bool");
		Type bt2 = TypeTable.getInstance().getType("BT_Numeric");

		Type ap1 = new ApplicationType(bt1, vt1);
		Type ap2 = new ApplicationType(vt2, bt2);

		TypeTable.getInstance().registerFinalType(ap1);
		TypeTable.getInstance().registerFinalType(ap2);

		TypeUtils.unify(ap1, ap2);

		Type t = TypeUtils.updateType(ap1);

		assertEquals("(BT_Bool->BT_Numeric)", TypeTable.getInstance().getType(t.getName()).getName());
	}

	@Test
	public void testUnifyApplicationList() {
		Type vt1 = TypeTable.getInstance().createVaryingType();
		Type vt2 = TypeTable.getInstance().createVaryingType();
		Type vt3 = TypeTable.getInstance().createVaryingType();

		Type lt = new ListType(vt1);
		ApplicationType ap = new ApplicationType(vt2, vt3);
		TypeTable.getInstance().registerFinalType(lt);
		TypeTable.getInstance().registerFinalType(ap);


		assertThrows(TypeInferenceException.class, () -> TypeUtils.unify(lt, ap));
		assertThrows(TypeInferenceException.class, () -> TypeUtils.unify(ap, lt));

	}

	@Test
	public void testListUnification() {
		Type vt1 = TypeTable.getInstance().createVaryingType();

		Type lt1 = new ListType(vt1);
		Type lt2 = new ListType(TypeTable.getInstance().getType("BT_Numeric"));

		TypeTable.getInstance().registerFinalType(lt1);
		TypeTable.getInstance().registerFinalType(lt2);

		TypeUtils.unify(lt1, lt2);

		lt1 = TypeUtils.updateType(lt1);

		assertEquals("[BT_Numeric]", lt1.getName());
	}

	@Test
	public void testListUnificationFail() {

		Type lt1 = new ListType(TypeTable.getInstance().getType("BT_Bool"));
		Type lt2 = new ListType(TypeTable.getInstance().getType("BT_Numeric"));

		TypeTable.getInstance().registerFinalType(lt1);
		TypeTable.getInstance().registerFinalType(lt2);

		assertThrows(TypeInferenceException.class, () -> TypeUtils.unify(lt1, lt2));
	}


	@Test
	public void testPrettyPrint() {
		Type g = BasicUtils.getGeneric();
		Type t0 = BasicUtils.getBool();
		Type t1 = new ApplicationType(new ListType(BasicUtils.getByte()), g);
		Type t2 = new ApplicationType(new ListType(BasicUtils.getByte()), g);
		Type t3 = new ApplicationType(new ListType(BasicUtils.getByte()), g);

		Type t4 = new ApplicationType(t2, t3);
		Type t5 = new ApplicationType(t1, t4);
		Type t6 = new ApplicationType(t0, t5);

		System.out.println(TypeUtils.generatePrettyName(t6));
	}
}
