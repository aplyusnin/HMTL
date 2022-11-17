package ru.nsu.fit.hmtl.inference.context;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.hmtl.inference.types.BasicTypeBuilder;
import ru.nsu.fit.hmtl.inference.types.DefaultTypeBuilder;
import ru.nsu.fit.hmtl.inference.types.Type;

import static org.mockito.Mockito.*;

public class DefaultContextTest {

	@Test
	public void testSimpleNamedTypesInContext() {
		Context parentContext = mock(Context.class);
		Type v = new BasicTypeBuilder().setName("Int").build();
		Type f = new DefaultTypeBuilder().setLeftHandSide(v).setRightHandSide(v).build();
		when(parentContext.getType("f")).thenReturn(f);
		DefaultContext context = new DefaultContext(parentContext);

		context.addType("v", v, false);
		Assert.assertThrows(ContextException.class, () -> context.addType("v", f, false));
		Assert.assertEquals(v.getName(), context.getType("v").getName());
		Assert.assertEquals(f.getName(), context.getType("f").getName());
		verify(parentContext, times(1)).getType("f");
		context.addType("f", f, false);
		Assert.assertEquals(f.getName(), context.getType("f").getName());
		verify(parentContext, times(1)).getType("f");
		context.addType("f", v, true);
		Assert.assertEquals(v.getName(), context.getType("f").getName());
		verify(parentContext, times(1)).getType("f");
	}
}
