package com.intrbiz.express.operator;

import java.util.Collection;

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
        context.checkOp();
        
        Operator op = this.getParameters().get(0) ;
        Object val = op.get(context,source);
        if (val instanceof Number)
        {
            return new Integer(((Number)val).intValue()) ;
        }
        else if (val instanceof Collection)
        {
            return ((Collection<?>) val).size();
        }
        else if (val instanceof Object[])
        {
            return ((Object[]) val).length;
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
