package ru.nsu.fit.hmtl.inference.context;

public class ContextUtils {

	public static Context createSubContext(Context context) {
		return new DefaultContext(context);
	}
}
