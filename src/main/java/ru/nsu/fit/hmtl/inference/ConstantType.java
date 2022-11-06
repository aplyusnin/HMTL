package ru.nsu.fit.hmtl.inference;

import ru.nsu.fit.hmtl.misc.Preconditions;

import java.util.*;

/** Class represents constant (non-functional) type during type inference. */
public class ConstantType {
	// Name of the Type
	private final String name;

	private Optional<ConstantType> parent;
	private final Set<ConstantType> children = new HashSet<>();

	public ConstantType(String name, ConstantType parent, ConstantType... children) {
		this.name = name;
		this.parent = Optional.ofNullable(parent);
		Collections.addAll(this.children, children);
		this.parent.ifPresent(x -> x.addChild(this));
	}

	public ConstantType(String name) {
		this(name, null);
	}

	/**
	 * Inherit this type from another.
	 * @param parent type to inherit from
	 */
	public void inherit(ConstantType parent) {
		// TODO: add preconditions to prevent circular dependencies
		Preconditions.checkState(this.parent.isEmpty(), "Type already has parent type");
		Preconditions.checkNotNull(parent, "Cannot tie to non-existing type");
		Preconditions.checkState(parent != this, "Cannot assign parent to itself");
		this.parent = Optional.of(parent);
		parent.children.add(this);
	}

	//
	// Getters ans setters
	//

	public void addChild(ConstantType child) {
		// TODO: add preconditions to prevent circular dependencies
		children.add(child);
	}

	public Set<ConstantType> getChildren() {
		return children;
	}

	public Optional<ConstantType> getParent() {
		return parent;
	}

	public String getName() {
		return name;
	}
}
