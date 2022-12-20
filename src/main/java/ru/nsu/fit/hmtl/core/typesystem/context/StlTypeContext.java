package ru.nsu.fit.hmtl.core.typesystem.context;

import ru.nsu.fit.hmtl.core.stl.StlFunctionDescriptor;
import ru.nsu.fit.hmtl.core.typesystem.types.ApplicationType;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class StlTypeContext implements TypeContext {
	private static StlTypeContext executionContext = null;

	public static StlTypeContext getInstance() {
		if (executionContext == null) executionContext = new StlTypeContext();
		return executionContext;
	}

	private final Map<String, Type> storage;

	private final TypeContext parent;

	private StlTypeContext() {
		storage = new HashMap<>();
		parent = new ParsingTypeContext();
		populateContext();
	}

	@SuppressWarnings("deprecation")
	private void populateContext() {
		Set<Class<?>> desc = new HashSet<>();
		try
		{
			InputStream stream = ClassLoader.getSystemClassLoader()
					.getResourceAsStream("ru/nsu/fit/hmtl/core/stl/functions/descriptors");
			assert stream != null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			desc = reader.lines()
					.filter(line -> line.endsWith(".class"))
					.map(this::getClass)
					.collect(Collectors.toSet());
		} catch (Exception e) {
			System.out.println("Fatal error: " + e.getMessage());
			System.out.println("Stack trace: " + Arrays.toString(Arrays.stream(e.getStackTrace()).toArray()));
			System.exit(1);
		}
		for (var d : desc) {
			try
			{
				StlFunctionDescriptor descriptor = (StlFunctionDescriptor) d.newInstance();

				Type result = descriptor.getFunctionType();

				List<Type> args = descriptor.getTypes();
				for (int i = args.size() - 1; i >= 0; i--) {
					result = new ApplicationType(args.get(i), result);
				}
				storage.put(descriptor.getName(), result);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private Class<?> getClass(String className) {
		try {
			String fullName = "ru.nsu.fit.hmtl.core.stl.functions.descriptors" + "."
					+ className.substring(0, className.lastIndexOf('.'));
			return Class.forName(fullName);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public Type lookup(String name) {
		if (storage.containsKey(name)) return storage.get(name);
		return parent.lookup(name);
	}

	@Override
	public TypeContext createSubContext() {
		return new TypeContextImpl(this);
	}

	@Override
	public void setType(String name, Type type)
	{

	}

}
