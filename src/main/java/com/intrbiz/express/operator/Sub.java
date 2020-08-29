package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class Sub extends BinaryOperator
{

	public Sub()
	{
		super("-");
	}

	public Sub(Operator l, Operator r)
	{
		this();
		this.setLeft(l);
		this.setRight(r);
	}

	@Override
	public Object get(ExpressContext context, Object source) throws ExpressException
	{
		if (this.getLeft() == null)
		{
			Object right = this.getRight().get(context, source);
			if (right instanceof Number)
			{
				Number nright = (Number) right;
				if (right instanceof Double)
				{
					// need a cast to double
					return new Double(nright.doubleValue() * -1D);
				}
				if (right instanceof Float)
				{
					// need to cast to float
					return new Float(nright.floatValue() * -1F);
				}
				if (right instanceof Long)
				{
					// need a cast to long
					return new Long(nright.longValue() * -1L);
				}
				if (right instanceof Integer)
				{
					// can cast as int
					return new Integer(nright.intValue() * -1);
				}
			}
		}
		else
		{
			Object left = this.getLeft().get(context, source);
			Object right = this.getRight().get(context, source);
			if (left instanceof Number && right instanceof Number)
			{
				Number nleft = (Number) left;
				Number nright = (Number) right;

				if (left instanceof Double || right instanceof Double)
				{
					// need a cast to double
					return new Double(nleft.doubleValue() - nright.doubleValue());
				}
				if (left instanceof Float || right instanceof Float)
				{
					// need to cast to float
					return new Float(nleft.floatValue() - nright.floatValue());
				}
				if (left instanceof Long || right instanceof Long)
				{
					// need a cast to long
					return new Long(nleft.longValue() - nright.longValue());
				}
				if (left instanceof Integer || right instanceof Integer)
				{
					// can cast as int
					return new Integer(nleft.intValue() - nright.intValue());
				}
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
