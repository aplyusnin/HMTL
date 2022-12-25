package ru.nsu.fit.hmtl.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of an execution context
 */
public class ExecutionContextImpl implements ExecutionContext {
	private final ExecutionContext parent;

	Map<String, Expression> layer;

	public ExecutionContextImpl(ExecutionContext parent) {
		this.parent = parent;
		layer = new HashMap<>();
	}

	@Override
	public Expression lookup(String name) {
		if (layer.containsKey(name)) {
			return layer.get(name);
		}
		return parent.lookup(name);
	}

	@Override
	public boolean contains(String name) {
		if (layer.containsKey(name)) return true;
		return parent.contains(name);
	}

	@Override
	public ExecutionContext createSubContext() {
		return new ExecutionContextImpl(this);
	}

	@Override
	public ExecutionContext createCopy() {
		ExecutionContextImpl ctx = new ExecutionContextImpl(parent);
		ctx.layer.putAll(layer);
		return ctx;
	}

	@Override
	public void setValue(String name, Expression newValue) {
		layer.put(name, newValue);
	}
}
