package com.intrbiz.express.operator;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class DoubleCast extends Function
{

    public DoubleCast()
    {
        super("double");
    }

    @Override
    public Object get(ELContext context,Object source) throws ELException
    {
        Operator op = this.getParameters().get(0) ;
        Object val = op.get(context,source);
        if (val instanceof Number)
        {
            return new Double(((Number)val).doubleValue()) ;
        }
        return null ;
    }

    @Override
    public void set(ELContext context, Object value,Object source) throws ELException
    {
    }
    
    
        
}
