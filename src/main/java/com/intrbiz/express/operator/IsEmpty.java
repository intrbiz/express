package com.intrbiz.express.operator;

import static com.intrbiz.Util.isEmpty;

import java.util.Collection;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class IsEmpty extends Function
{

    public IsEmpty()
    {
        super("isempty");
    }

    @SuppressWarnings("rawtypes")
	@Override
    public Object get(ELContext context, Object source) throws ELException
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
        
        return true;
    }
    
    
    
}
