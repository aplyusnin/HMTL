package ru.nsu.fit.hmtl.inference.context.basic;

import java.util.*;

public class BasicContextLoader {

	private final Iterator<String> types;


	public BasicContextLoader() {
		types = Arrays.asList(":Int", ":Bool", ":String").iterator();
	}

	String nextType() {
		if (types.hasNext()) {
			return types.next();
		} else {
			return null;
		}
	}
}
