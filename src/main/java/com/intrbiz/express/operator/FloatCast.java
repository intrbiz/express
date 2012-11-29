package com.intrbiz.express.operator;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class FloatCast extends Function
{
    
    public FloatCast()
    {
        super("float");
    }

    @Override
    public Object get(ELContext context,Object source) throws ELException
    {
        Operator op = this.getParameters().get(0) ;
        Object val = op.get(context,source);
        if (val instanceof Number)
        {
            return new Float(((Number)val).floatValue()) ;
        }
        return null ;
    }

    @Override
    public void set(ELContext context, Object value,Object source) throws ELException
    {
    }
    
    
        
}
