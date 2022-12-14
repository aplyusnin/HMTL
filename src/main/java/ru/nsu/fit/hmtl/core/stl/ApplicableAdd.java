package ru.nsu.fit.hmtl.core.stl;

import ru.nsu.fit.hmtl.core.Applicable;
import ru.nsu.fit.hmtl.core.TypedObject;
import ru.nsu.fit.hmtl.core.WrapperList;
import ru.nsu.fit.hmtl.inference.typesystem.types.BasicTypeBuilder;

public class ApplicableAdd implements Applicable {

	@Override
	public TypedObject apply(WrapperList args) {
		return new TypedObject((Integer)args.get(0).eval().getValue() + (Integer)args.get(0).eval().getValue(), new BasicTypeBuilder().setName(":Int").build());
	}

}
