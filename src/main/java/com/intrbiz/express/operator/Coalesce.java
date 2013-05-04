package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

/**
 *
 */
public class Coalesce extends Function
{

    public Coalesce()
    {
        super("coalesce");
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
    	for (Operator o : this.getParameters())
    	{
    		Object r = o.get(context,source);
    		if (r != null)
    			return r;
    	}
    	return null;
    }
    
    
    
}
