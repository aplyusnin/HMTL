package ru.nsu.fit.hmtl.core.typesystem.context;

import ru.nsu.fit.hmtl.core.lang.Function;
import ru.nsu.fit.hmtl.core.typesystem.types.Type;

import java.io.BufferedReader;
import java.io.File;
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
		parent = new ParsingTypeContext();
		storage = new HashMap<>();
		populateContext();
	}

	@SuppressWarnings({"deprecation", "ConstantConditions"})
	private void populateContext() {
		Set<Class<?>> desc = new HashSet<>();
		try
		{
			File file = new File(
					ClassLoader.getSystemClassLoader()
							.getResource("./ru/nsu/fit/hmtl/core/lang/").toURI());
			dfs(file, "ru/nsu/fit/hmtl/core/lang", desc);

		} catch (Exception e) {
			System.out.println("Fatal error: " + e.getMessage());
			System.out.println("Stack trace: " + Arrays.toString(Arrays.stream(e.getStackTrace()).toArray()));
			System.exit(1);
		}
		for (var d : desc) {
			try {
				if (d.getSuperclass().equals(Function.class)) {
					Function f = (Function) d.newInstance();
					storage.put(f.toString(), f.getType());
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private void dfs(File dir, String filepath, Set<Class<?>> cl) {
		if (!dir.exists() || !dir.isDirectory()) return;
		File[] fs = dir.listFiles();
		if (fs == null) return;
		for (var f : fs) {
			dfs(f, filepath + '/' + f.getName(), cl);
		}
		try {
			InputStream stream = dir.toURI().toURL().openStream();
			assert stream != null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

			cl.addAll(reader.lines()
					          .filter(line -> line.endsWith(".class"))
					          .map(x -> getClass(filepath, x))
					          .collect(Collectors.toSet()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private Class<?> getClass(String pack, String className) {
		try {
//			System.out.println("!" + pack + " " + className);
			String pack1 = pack.replace("/", ".");
			String fullName = pack1 + "."
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
