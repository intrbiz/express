package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class DoubleCast extends Function
{
    public DoubleCast()
    {
        super("double");
    }

    @Override
    public Object get(ExpressContext context,Object source) throws ExpressException
    {
        context.checkOp();
        
        Operator op = this.getParameters().get(0) ;
        Object val = op.get(context,source);
        if (val instanceof Number)
        {
            return new Double(((Number)val).doubleValue()) ;
        }
        else if (val instanceof String)
        {
            return Double.parseDouble((String) val);
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
