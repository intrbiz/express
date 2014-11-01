package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class Equals extends BinaryOperator
{

	public Equals()
	{
		super("==");
	}

	protected Equals(String name)
	{
		super(name);
	}

	public Equals(Operator l, Operator r)
	{
		this();
		this.setLeft(l);
		this.setRight(r);
	}

	@Override
	public Boolean get(ExpressContext context, Object source) throws ExpressException
	{
		Object left = this.getLeft().get(context, source);
		Object right = this.getRight().get(context, source);
		if (left == null && right == null)
			return new Boolean(true);
		if (left == null || right == null)
			return new Boolean(false);
		if (left instanceof Number && right instanceof Number)
		{
			if ((left instanceof Long || left instanceof Integer) && (right instanceof Long || right instanceof Integer))
			{
				return ((Number) left).longValue() == ((Number) right).longValue();
			}
			else
			{
				return ((Number) left).doubleValue() == ((Number) right).doubleValue();
			}
		}
		return new Boolean(left.equals(right));
	}
	
	@Override
    public boolean isIdempotent()
    {
        return true;
    }
}
