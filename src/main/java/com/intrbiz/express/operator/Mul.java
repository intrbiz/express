package com.intrbiz.express.operator;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class Mul extends BinaryOperator
{

    public Mul()
    {
        super("*");
    }
    
    public Mul(Operator l, Operator r)
    {
    	this();
    	this.setLeft(l);
    	this.setRight(r);
    }

    @Override
    public Object get(ELContext context, Object source) throws ELException
    {
        Object left = this.getLeft().get(context, source);
        Object right = this.getRight().get(context, source);

        if (left instanceof Number && right instanceof Number)
        {
            Number nleft = (Number) left ;
            Number nright = (Number) right ;

            if (left instanceof Double || right instanceof Double)
            {
                // need a cast to double
               return new Double(nleft.doubleValue() * nright.doubleValue());
            }
            if (left instanceof Float || right instanceof Float)
            {
                // need to cast to float
                return new Float(nleft.floatValue() * nright.floatValue());
            }
            if (left instanceof Long || right instanceof Long)
            {
                // need a cast to long
                return new Long(nleft.longValue() * nright.longValue());
            }
            if (left instanceof Integer && right instanceof Integer)
            {
                // can cast as int
                return new Integer(nleft.intValue() * nright.intValue());
            }
        }

        return null;
    }

    @Override
    public void set(ELContext context, Object value, Object source) throws ELException
    {
    }
}
