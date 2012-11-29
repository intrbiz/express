package com.intrbiz.express.operator;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class IntCast extends Function
{

    public IntCast()
    {
        super("int");
    }

    @Override
    public Object get(ELContext context,Object source) throws ELException
    {
        Operator op = this.getParameters().get(0) ;
        Object val = op.get(context,source);
        if (val instanceof Number)
        {
            return new Integer(((Number)val).intValue()) ;
        }
        return null ;
    }

    @Override
    public void set(ELContext context, Object value,Object source) throws ELException
    {
    }
    
    
        
}
