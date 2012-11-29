package com.intrbiz.express.operator;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class LogicalAnd extends BinaryOperator
{

	public LogicalAnd()
	{
		super("&&");
	}

	public LogicalAnd(Operator l, Operator r)
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
		if (left instanceof Boolean && right instanceof Boolean)
		{
			boolean l = ((Boolean) left).booleanValue();
			boolean r = ((Boolean) right).booleanValue();
			return l && r;
		}
		return new Boolean(false);
	}

	@Override
	public void set(ELContext context, Object value, Object source) throws ELException
	{
	}
}
