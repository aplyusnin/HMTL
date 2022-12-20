package ru.nsu.fit.hmtl.core.stl;

import ru.nsu.fit.hmtl.core.Expression;
import ru.nsu.fit.hmtl.core.ExecutionContext;
import ru.nsu.fit.hmtl.core.ExecutionContextImpl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class StlExecutionContext implements ExecutionContext {

	private static StlExecutionContext executionContext = null;

	public static StlExecutionContext getInstance() {
		if (executionContext == null) executionContext = new StlExecutionContext();
		return executionContext;
	}

	private final Map<String, Expression> storage;

	private StlExecutionContext() {
		storage = new HashMap<>();
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
				Expression.Builder builder = new Expression.Builder();

				builder.setContext(this);
				builder.setCore(descriptor.getFunction());
				for (var arg : descriptor.getArgs()) {
					builder.addName(arg);
				}
				for (var type : descriptor.getTypes()) {
					builder.addType(type);
				}

				Expression wrapper = builder.build();
				storage.put(d.getName(), wrapper);

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
	public Expression lookup(String name) {
		return storage.get(name);
	}

	@Override
	public boolean contains(String name) {
		return storage.containsKey(name);
	}

	@Override
	public ExecutionContext createSubContext() {
		return new ExecutionContextImpl(this);
	}

	@Override
	public void setValue(String name, Expression newValue) {
	}
}
