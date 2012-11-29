package com.intrbiz.express.dynamic;

import com.intrbiz.express.ELContext;

public interface EntityProxy
{
	Object getEntity(ELContext ctx, Object source);
}
