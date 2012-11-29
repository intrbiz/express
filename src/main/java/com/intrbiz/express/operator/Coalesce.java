package com.intrbiz.express.operator;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

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
    public Object get(ELContext context, Object source) throws ELException
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
