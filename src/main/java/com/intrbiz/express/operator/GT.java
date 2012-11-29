package com.intrbiz.express.operator;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class GT extends BinaryOperator
{
    
    public GT()
    {
        super(">");
    }
    
	public GT(Operator l, Operator r)
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
               return nleft.doubleValue() > nright.doubleValue();
            }
            if (left instanceof Float || right instanceof Float)
            {
                return nleft.floatValue() > nright.floatValue();
            }
            if (left instanceof Long || right instanceof Long)
            {
                return nleft.longValue() > nright.longValue();
            }
            if (left instanceof Integer && right instanceof Integer)
            {
                return nleft.intValue() > nright.intValue();
            }
        }
        return false;
    }

    @Override
    public void set(ELContext context, Object value, Object source) throws ELException
    {
    }
}
