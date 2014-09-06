package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class IsNull extends Function
{

    public IsNull()
    {
        super("isnull");
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        Operator ev = this.getParameters().get(0);
        Object evo = ev.get(context,source);
        return evo == null;
    }
    
    @Override
    public boolean isIdempotent()
    {
        return true;
    }
}
