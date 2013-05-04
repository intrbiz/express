package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class Pow extends BinaryOperator
{

    public Pow()
    {
        super("^");
    }
    
    public Pow(Operator l, Operator r)
    {
    	this();
    	this.setLeft(l);
    	this.setRight(r);
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
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
               return new Double(Math.pow(nleft.doubleValue() ,  nright.doubleValue()));
            }
            if (left instanceof Float || right instanceof Float)
            {
                // need to cast to float
                return new Double(Math.pow(nleft.floatValue() , nright.floatValue()));
            }
            if (left instanceof Long || right instanceof Long)
            {
                // need a cast to long
                return new Double(Math.pow(nleft.longValue() , nright.longValue()));
            }
            if (left instanceof Integer && right instanceof Integer)
            {
                // can cast as int
                return new Double(Math.pow(nleft.intValue() , nright.intValue()));
            }
        }

        return null;
    }

    @Override
    public void set(ExpressContext context, Object value, Object source) throws ExpressException
    {
    }
}
