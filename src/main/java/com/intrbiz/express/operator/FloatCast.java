package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class FloatCast extends Function
{
    
    public FloatCast()
    {
        super("float");
    }

    @Override
    public Object get(ExpressContext context,Object source) throws ExpressException
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
    public void set(ExpressContext context, Object value,Object source) throws ExpressException
    {
    }
    
    
        
}
