package com.intrbiz.express.operator;

import static com.intrbiz.Util.*;

import java.util.Collection;
import java.util.Map;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class IsEmpty extends Function
{

    public IsEmpty()
    {
        super("isempty");
    }

    @SuppressWarnings("rawtypes")
	@Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        Operator ev = this.getParameters().get(0);
        Object evo = ev.get(context,source);

        if (evo instanceof String)
        {
            String s = (String) evo ;
            return isEmpty(s);
        }
        else if (evo instanceof Collection)
        {
        	return ((Collection) evo).isEmpty();
        }
        else if (evo instanceof Map)
        {
            return ((Map) evo).isEmpty();
        }
        
        return true;
    }
    
    
    
}
