package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class Wrapped extends UnaryOperator
{

	public Wrapped()
	{
		super("Wrapped");
	}

	public Wrapped(Operator o)
	{
		this();
		this.setOperator(o);
	}

	@Override
	public Object get(ExpressContext context, Object source) throws ExpressException
	{
		Operator op = this.getOperator();
		return op.get(context, source);
	}

	@Override
	public void set(ExpressContext context, Object value, Object source) throws ExpressException
	{
		Operator op = this.getOperator();
		op.set(context, value, source);
	}

	@Override
	public String toString()
	{
		return "#{" + this.getOperator().toString() + "}";
	}
}
