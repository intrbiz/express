package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

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
	public Object get(ExpressContext context, Object source) throws ExpressException
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
	public void set(ExpressContext context, Object value, Object source) throws ExpressException
	{
	}
}
