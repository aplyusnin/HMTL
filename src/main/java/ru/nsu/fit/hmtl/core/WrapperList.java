package ru.nsu.fit.hmtl.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Wrapper over {@link List} to ease codegen.
 */
public class WrapperList implements Iterable<ApplicableWrapper> {

	private final List<ApplicableWrapper> wrapperList;

	public WrapperList() {
		wrapperList = new ArrayList<>();
	}

	public void add(ApplicableWrapper wrapper) {
		wrapperList.add(wrapper);
	}

	public ApplicableWrapper get(int id) {
		return wrapperList.get(id);
	}

	@Override
	public Iterator<ApplicableWrapper> iterator() {
		return wrapperList.iterator();
	}
}
