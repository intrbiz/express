package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class LongCast extends Function
{
    
    public LongCast()
    {
        super("Long");
    }

    @Override
    public Object get(ExpressContext context,Object source) throws ExpressException
    {
        context.checkOp();
        
        Operator op = this.getParameters().get(0) ;
        Object val = op.get(context,source);
        if (val instanceof Number)
        {
            return new Long(((Number)val).longValue()) ;
        }
        else if (val instanceof String)
        {
            return Long.parseLong((String) val);
        }
        return null ;
    }

    @Override
    public void set(ExpressContext context, Object value,Object source) throws ExpressException
    {
    }
    
    @Override
    public boolean isIdempotent()
    {
        return true;
    }
}
