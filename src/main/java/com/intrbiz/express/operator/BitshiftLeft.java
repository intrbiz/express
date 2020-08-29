package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class BitshiftLeft extends BinaryOperator
{
    
    public BitshiftLeft()
    {
        super("<<");
    }
    
    public BitshiftLeft(Operator l, Operator r)
    {
    	this();
    	this.setLeft(l);
    	this.setRight(r);
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        context.checkOp();
        
        Object left = this.getLeft().get(context, source);
        Object right = this.getRight().get(context, source);
        // numeric bitwise
        if (left instanceof Number && right instanceof Number)
        {
            Number nleft = (Number) left;
            Number nright = (Number) right;
            if (left instanceof Long)
            {
                return nleft.longValue() << nright.intValue();
            }
            if (left instanceof Integer)
            {
                return nleft.intValue() << nright.intValue();
            }
            if (left instanceof Byte)
            {
                return nleft.byteValue() << nright.intValue();
            }
        }
        return null;
    }

    @Override
    public void set(ExpressContext context, Object value, Object source) throws ExpressException
    {
    }
    
    @Override
    public boolean isIdempotent()
    {
        return true;
    }
}
