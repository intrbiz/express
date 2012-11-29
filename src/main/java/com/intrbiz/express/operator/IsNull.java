package com.intrbiz.express.operator;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class IsNull extends Function
{

    public IsNull()
    {
        super("isnull");
    }

    @Override
    public Object get(ELContext context, Object source) throws ELException
    {
        Operator ev = this.getParameters().get(0);
        
        Object evo = ev.get(context,source);

        return evo == null;
        
    }
    
    
    
}
