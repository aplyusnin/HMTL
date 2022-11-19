package ru.nsu.fit.hmtl.inference.context;

public interface ContextLookupProvider {

	/**
	 * Process type lookup in attached context
	 * @param name of variable
	 * @return index of constant
	 */
	int lookupType(String name);
}
