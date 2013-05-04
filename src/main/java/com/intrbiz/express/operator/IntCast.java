package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class IntCast extends Function
{

    public IntCast()
    {
        super("int");
    }

    @Override
    public Object get(ExpressContext context,Object source) throws ExpressException
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
    public void set(ExpressContext context, Object value,Object source) throws ExpressException
    {
    }
    
    
        
}
