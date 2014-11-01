package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class StringCast extends Function
{
    
    public StringCast()
    {
        super("string");
    }

    @Override
    public Object get(ExpressContext context,Object source) throws ExpressException
    {
        Operator op = this.getParameters().get(0) ;
        Object val = op.get(context,source);
        return String.valueOf(val);
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
