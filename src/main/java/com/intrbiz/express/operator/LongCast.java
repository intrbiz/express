package com.intrbiz.express.operator;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class LongCast extends Function
{
    
    public LongCast()
    {
        super("Long");
    }

    @Override
    public Object get(ELContext context,Object source) throws ELException
    {
        Operator op = this.getParameters().get(0) ;
        Object val = op.get(context,source);
        if (val instanceof Number)
        {
            return new Long(((Number)val).longValue()) ;
        }
        return null ;
    }

    @Override
    public void set(ELContext context, Object value,Object source) throws ELException
    {
    }
    
    
        
}
