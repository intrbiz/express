package com.intrbiz.express.operator;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class LogicalNot extends UnaryOperator
{

	public LogicalNot()
	{
		super("!");
	}

	public LogicalNot(Operator o)
	{
		this();
		this.setOperator(o);
	}

	@Override
	public Object get(ELContext context, Object source) throws ELException
	{
		Operator val = this.getOperator();
		Object eval = val.get(context, source);
		if (eval instanceof Boolean)
		{
			boolean beval = ((Boolean) eval).booleanValue();
			return new Boolean(!beval);
		}
		return null;
	}

	@Override
	public void set(ELContext context, Object value, Object source) throws ELException
	{
	}
}
