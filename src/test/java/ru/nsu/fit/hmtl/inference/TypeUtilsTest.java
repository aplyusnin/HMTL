package ru.nsu.fit.hmtl.inference;

import org.junit.Assert;
import org.junit.Test;

public class TypeUtilsTest {

	@Test
	public void testEqualityInferenceTypes() {
		InferenceType type1 = new InferenceType(0);
		InferenceType type2 = new InferenceType(1);
		Assert.assertTrue(TypeUtils.areEqual(type1, type2));
	}

	@Test
	public void testEqualityInferenceFinalTypes() {
		InferenceType type1 = new InferenceType(0, "String");
		InferenceType type2 = new InferenceType(1, "Integer");
		Assert.assertFalse(TypeUtils.areEqual(type1, type2));
	}
}
