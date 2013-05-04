package com.intrbiz.express.dynamic;

import com.intrbiz.express.ExpressContext;

public interface EntityProxy
{
	Object getEntity(ExpressContext ctx, Object source);
}
