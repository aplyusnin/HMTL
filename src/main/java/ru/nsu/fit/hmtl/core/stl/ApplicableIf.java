package ru.nsu.fit.hmtl.core.stl;

import ru.nsu.fit.hmtl.core.Applicable;
import ru.nsu.fit.hmtl.core.TypedObject;
import ru.nsu.fit.hmtl.core.WrapperList;

public class ApplicableIf implements Applicable {

	@Override
	public TypedObject apply(WrapperList args)
	{
		if ((Boolean) args.get(0).eval().getValue()) {
			return args.get(1).eval();
		} else {
			return args.get(2).eval();
		}
	}

}
