package com.intrbiz.express.operator;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class StringCast extends Function
{
    
    public StringCast()
    {
        super("string");
    }

    @Override
    public Object get(ELContext context,Object source) throws ELException
    {
        Operator op = this.getParameters().get(0) ;
        Object val = op.get(context,source);
        return String.valueOf(val);
    }

    @Override
    public void set(ELContext context, Object value,Object source) throws ELException
    {
    }
    
    
        
}
